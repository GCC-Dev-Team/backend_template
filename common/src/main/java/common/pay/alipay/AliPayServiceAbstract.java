package common.pay.alipay;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;


import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.AlipayTradeRefundModel;
import com.alipay.v3.model.AlipayTradeRefundResponseModel;
import com.alipay.v3.util.AlipaySignature;
import common.exception.CommonException;
import common.pay.alipay.model.bo.*;
import common.pay.alipay.payments.AliH5PePayService;
import common.utils.ParamsUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付服务
 * <a href="https://github.com/alipay/alipay-sdk-java-all/tree/master/v3">支付宝</a>
 *
 * @author I Nhrl
 */
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AliPayServiceAbstract {
    protected static final Map<String, AliAppPayConfig> PAY_CONFIG_MAP = new HashMap<>();
    protected static final Map<String, String> PAY_CHANNEL_MAP = new HashMap<>();

    private static String defaultConfigKey;

    public static void setDefaultConfigKey(String defaultConfigKey) {
        AliPayServiceAbstract.defaultConfigKey = defaultConfigKey;
    }

    public static String getDefaultConfigKey() {
        return AliPayServiceAbstract.defaultConfigKey;
    }

    /**
     * 更新支付宝支付配置
     */
    @PostConstruct
    public abstract void updateAliPayConfig();

    /**
     * 读取PAY_CONFIG_MAP
     *
     * @param key 键
     * @return 支付宝支付配置
     */
    public abstract AliAppPayConfig get(String key);

    /**
     * 获取支付渠道id
     * @param appId 支付宝 应用id
     * @return 支付渠道id
     */
    public abstract String getPayChannel(String appId);

    /**
     * 支付宝支付相关事件回调处理
     *
     * @param transaction 交易、订单信息
     * @return 相关业务是否执行完成
     */
    public abstract Boolean aliTransactionCallbackHandler(Map<String, String> transaction);

    /**
     * 支付宝退款相关事件回调处理
     *
     * @param refundNotification 退款订单信息
     * @return 相关业务是否执行完成
     */
    public abstract Boolean aliRefundNotificationCallbackHandler(Map<String, String> refundNotification);


    /**
     * 支付宝预支付-统一下单
     *
     * @param bo         预支付信息
     * @param payChannel 支付渠道
     * @return 预支付回调
     */
    public AliPrepayResponseBO unifiedOrder(AliPrepayUnifiedBO bo, String payChannel) {
        AliAppPayConfig payConfig = get(payChannel);
        if (ObjectUtil.isEmpty(payConfig)) {
            throw new CommonException("Alipay payment is not enabled for this product");
        }
        return switch (bo.getType()) {
            case H5_PE -> AliH5PePayService.prepay(payConfig, bo);
            default -> throw new CommonException("This method is not supported");
        };
    }

    /**
     * 支付宝 支付订单查询
     *
     * @param bo 查询订单信息
     * @param payChannel 支付渠道
     * @return 订单信息
     */
    public AliQueryOrderResponseBO queryOrder(AliQueryOrderBO bo, String payChannel) {
        AliAppPayConfig payConfig = get(payChannel);
        return switch (bo.getType()) {
            case H5_PE -> AliH5PePayService.queryOrderById(payConfig, bo.getOutTradeNo());
            default -> throw new CommonException("This method is not supported");
        };
    }

    /**
     * 微信关闭订单
     *
     * @param bo 关闭订单信息
     * @param payChannel 支付渠道
     */
    public void closeOrder(AliCloseOrderBO bo, String payChannel) {
        AliAppPayConfig payConfig = get(payChannel);
        switch (bo.getType()) {
            case H5_PE -> AliH5PePayService.closeOrder(payConfig, bo.getOutTradeNo());
            default -> throw new CommonException("Close weOrder error, order type does not exist");
        }
    }

    /**
     * 退款申请
     *
     * @param orderId 退款的订单
     * @param refundOrderId 退款单号
     * @param refundPrice 退款金额(元|小数点后两位)
     * @param payChannel 支付渠道
     * @return 退款信息
     */
    public AlipayTradeRefundResponseModel refunds(String orderId, String refundOrderId, BigDecimal refundPrice, String payChannel) {
        AliAppPayConfig payConfig = get(payChannel);
        AlipayTradeApi api = new AlipayTradeApi(payConfig.getApiClient());
        AlipayTradeRefundModel data = new AlipayTradeRefundModel();

        // 设置商户订单号
        data.setOutTradeNo(orderId);
        // 设置退款金额
        data.setRefundAmount(NumberUtil.toStr(refundPrice));


        // 设置退款请求号
        data.setOutRequestNo(refundOrderId);
        try {
            return api.refund(data);
        } catch (ApiException e) {
            log.error("支付宝退款失败",e);
            throw new CommonException(e.getMessage());
        }
    }
//
//    /**
//     * 查询退款订单
//     *
//     * @param request 订单数据
//     * @param payChannel 支付渠道
//     * @return 退款信息
//     */
//    public Refund queryRefund(QueryByOutRefundNoRequest request, String payChannel) {
//        AliAppPayConfig payConfig = get(payChannel);
//        RefundService service = new RefundService.Builder().config(payConfig.getWxPayConfig()).build();
//
//        return service.queryByOutRefundNo(request);
//    }

    /**
     * 回调处理
     *
     * @param request    回调请求
     * @return 回调结果-ResponseEntity
     */
    public ResponseEntity<String> call(HttpServletRequest request) {
        // 将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = ParamsUtil.convertRequestParamsToMap(request);
        String payChannel = getPayChannel(params.get("app_id"));
        AliAppPayConfig payConfig = get(payChannel);
        if (payConfig == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, payConfig.getAlipayPublicKey(), "utf-8", "RSA2");
        } catch (ApiException e) {
            throw new CommonException("Verification of signature using SDK failed");
        }
        if (!signVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean isPayEquals = StrUtil.isBlank(params.get("refund_fee"));

        if (isPayEquals) {
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            if (!Boolean.TRUE.equals(aliTransactionCallbackHandler(params))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            if (!Boolean.TRUE.equals(aliRefundNotificationCallbackHandler(params))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // 处理成功，返回 200 OK 状态码
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
