package common.pay.wechatpay;


import com.wechat.pay.java.core.http.Constant;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import common.exception.CommonException;
import common.pay.wechatpay.model.bo.*;
import common.pay.wechatpay.payments.WeAppPayService;
import common.pay.wechatpay.payments.WeH5PayService;
import common.pay.wechatpay.payments.WeJsapiPayService;
import common.pay.wechatpay.payments.WeNativePayService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付服务
 * <a href="https://github.com/wechatpay-apiv3/wechatpay-java">微信</a>
 *使用需要继承然后书写业务初始化
 * @author I Nhrl
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class WeChatPayServiceAbstract {
    protected static final Map<String, WeChatPayConfig> PAY_CONFIG_MAP = new HashMap<>();

    private static String defaultConfigKey;

    public static void setDefaultConfigKey(String defaultConfigKey) {
        WeChatPayServiceAbstract.defaultConfigKey = defaultConfigKey;
    }

    public static String getDefaultConfigKey() {
        return WeChatPayServiceAbstract.defaultConfigKey;
    }

    /**
     * 更新微信支付配置
     */
    @PostConstruct
    public abstract void wxPayConfigInit();

    /**
     * 微信支付相关事件回调处理
     *
     * @param transaction 交易、订单信息
     * @return 相关业务是否执行完成
     */
    public abstract Boolean weTransactionCallbackHandler(Transaction transaction, String payChannelId);

    //Todo 分段退款没有完成
    /**
     * 微信退款相关事件回调处理
     *
     * @param refundNotification 退款订单信息
     * @return 相关业务是否执行完成
     */
    public abstract Boolean weRefundNotificationCallbackHandler(RefundNotification refundNotification, String payChannelId);



    /**
     * 读取PAY_CONFIG_MAP
     * @param key 键
     * @return 微信支付配置
     */
    public abstract WeChatPayConfig get(String key);

    /**
     * 微信预支付-统一下单
     *
     * @param bo 预支付信息
     * @param payChannel 支付渠道
     * @return 预支付回调
     */
    public WePrepayResponseBO unifiedOrder(WePrepayUnifiedBO bo, String payChannel) {
        WeChatPayConfig payConfig = get(payChannel);
        return switch (bo.getType()) {
            case APP -> WeAppPayService.prepayWithRequestPayment(payConfig, bo);
            case H5 -> WeH5PayService.prepay(payConfig, bo);
            case JSAPI -> WeJsapiPayService.prepayWithRequestPayment(payConfig, bo);
            case NATIVE -> WeNativePayService.prepay(payConfig, bo);
        };
    }

    /**
     * 微信支付订单查询
     *
     * @param bo 查询订单信息
     * @param payChannel 支付渠道
     * @return 订单信息
     */
    public WeQueryOrderResponseBO queryOrder(WeQueryOrderBO bo, String payChannel) {
        WeChatPayConfig payConfig = get(payChannel);
        return switch (bo.getType()) {
            case APP -> bo.getIdType() ?
                    WeAppPayService.queryOrderById(payConfig, bo.getOrderId()) :
                    WeAppPayService.queryOrderByOutTradeNo(payConfig, bo.getOrderId());
            case H5 -> bo.getIdType() ?
                    WeH5PayService.queryOrderById(payConfig, bo.getOrderId()) :
                    WeH5PayService.queryOrderByOutTradeNo(payConfig, bo.getOrderId());
            case JSAPI -> bo.getIdType() ?
                    WeJsapiPayService.queryOrderById(payConfig, bo.getOrderId()) :
                    WeJsapiPayService.queryOrderByOutTradeNo(payConfig, bo.getOrderId());
            case NATIVE -> bo.getIdType() ?
                    WeNativePayService.queryOrderById(payConfig, bo.getOrderId()) :
                    WeNativePayService.queryOrderByOutTradeNo(payConfig, bo.getOrderId());
        };
    }

    /**
     * 微信关闭订单
     *
     * @param bo 关闭订单信息
     * @param payChannel 支付渠道
     */
    public void closeOrder(WeCloseOrderBO bo, String payChannel) {
        WeChatPayConfig payConfig = get(payChannel);
        switch (bo.getType()) {
            case APP -> WeAppPayService.closeOrder(payConfig, bo.getOutTradeNo());
            case H5 -> WeH5PayService.closeOrder(payConfig, bo.getOutTradeNo());
            case JSAPI -> WeJsapiPayService.closeOrder(payConfig, bo.getOutTradeNo());
            case NATIVE -> WeNativePayService.closeOrder(payConfig, bo.getOutTradeNo());
            default -> throw new CommonException("Close weOrder error, order type does not exist");
        }
    }

    /**
     * 退款申请
     *
     * @param refundPrice 退款金额
     * @param payChannel 支付渠道
     * @param totalPrice 退款订单的总金额
     * @return 退款信息
     */
    public Refund refunds(String orderId, String refundOrderId, String notifyUrl, Long refundPrice, Long totalPrice, String payChannel) {
        WeChatPayConfig payConfig = get(payChannel);
        AmountReq amount = new AmountReq();
        amount.setRefund(refundPrice);
        amount.setTotal(totalPrice);
        amount.setCurrency("CNY");
        CreateRequest request = new CreateRequest();
        request.setOutRefundNo(refundOrderId);
        request.setOutTradeNo(orderId);
        request.setAmount(amount);
        request.setNotifyUrl(notifyUrl);

        RefundService service = new RefundService.Builder().config(payConfig.getWxPayConfig()).build();

        return service.create(request);
    }

    /**
     * 查询退款订单
     *
     * @param request 订单数据
     * @param payChannel 支付渠道
     * @return 退款信息
     */
    public Refund queryRefund(QueryByOutRefundNoRequest request, String payChannel) {
        WeChatPayConfig payConfig = get(payChannel);
        RefundService service = new RefundService.Builder().config(payConfig.getWxPayConfig()).build();

        return service.queryByOutRefundNo(request);
    }

    /**
     * 回调处理
     *
     * @param request 回调请求
     * @param payChannel 支付配置 键
     * @param isPay true 支付通知、false 退款通知
     * @return 回调结果-ResponseEntity
     */
    public ResponseEntity<String> call(HttpServletRequest request, String payChannel, Boolean isPay) {
        WeChatPayConfig payConfig = get(payChannel);
        if (payConfig == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = inputStream.read(buffer)) != -1; ) {
                body.write(buffer, 0, length);
            }
        } catch (IOException ex) {
            log.error("支付回调，读取数据流异常", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String requestBody = body.toString();
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(request.getHeader(Constant.WECHAT_PAY_SERIAL))
                .nonce(request.getHeader(Constant.WECHAT_PAY_NONCE))
                .signature(request.getHeader(Constant.WECHAT_PAY_SIGNATURE))
                .timestamp(request.getHeader(Constant.WECHAT_PAY_TIMESTAMP))
                //com.wechat.pay.java.core.notification.RequestParam.RequestParam //signType
                .signType(request.getHeader("Wechatpay-Signature-Type"))
                .body(requestBody)
                .build();
        NotificationParser parser = new NotificationParser(payConfig.getNotificationConfig());
        boolean isPayEquals = Boolean.TRUE.equals(isPay);
        Transaction transaction = null;
        RefundNotification refundNotification = null;
        try {
            if (isPayEquals){
                transaction = parser.parse(requestParam, Transaction.class);
            }else {
                refundNotification = parser.parse(requestParam, RefundNotification.class);
            }
        } catch (Exception e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("we-Transaction::NotificationParser.parse error:", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (isPayEquals){
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            if (!Boolean.TRUE.equals(weTransactionCallbackHandler(transaction,payChannel))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }else {
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            if (!Boolean.TRUE.equals(weRefundNotificationCallbackHandler(refundNotification,payChannel))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // 处理成功，返回 200 OK 状态码
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public static Long priceConversion(@NotNull BigDecimal price){
        BigDecimal bigDecimal = price.multiply(BigDecimal.valueOf(100));
        return bigDecimal.longValue();
    }
}
