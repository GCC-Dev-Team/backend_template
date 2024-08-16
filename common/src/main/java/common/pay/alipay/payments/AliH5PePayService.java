package common.pay.alipay.payments;

import cn.hutool.json.JSONUtil;

import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.*;
import com.alipay.v3.util.GenericExecuteApi;
import common.exception.CommonException;
import common.pay.alipay.AliAppPayConfig;
import common.pay.alipay.model.bo.AliPrepayResponseBO;
import common.pay.alipay.model.bo.AliPrepayUnifiedBO;
import common.pay.alipay.model.bo.AliQueryOrderResponseBO;
import common.pay.alipay.model.enums.AliPaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;



/**
 * 支付宝手机网页支付
 * @author I Nhrl
 */
@Slf4j
public class AliH5PePayService {
    /** 关闭订单 */
    public static void closeOrder(AliAppPayConfig payConfig, @NotBlank String outTradeNo) {
        ApiClient apiClient = payConfig.getApiClient();
        AlipayTradeApi api = new AlipayTradeApi(apiClient);
        AlipayTradeCloseModel data = new AlipayTradeCloseModel();
        data.setOutTradeNo(outTradeNo);
        try {
            AlipayTradeCloseResponseModel response = api.close(data);
            log.info("支付宝订单关闭: {}", JSONUtil.toJsonStr(response));
        } catch (ApiException e) {
            log.error("关闭订单失败", e);
        }
    }
    /** H5支付下单 */
    public static AliPrepayResponseBO prepay(AliAppPayConfig payConfig, AliPrepayUnifiedBO bo) {
        ApiClient apiClient = payConfig.getApiClient();
        GenericExecuteApi api = new GenericExecuteApi(apiClient);

        try {
            String pageRedirectionData = api.pageExecute("alipay.trade.wap.pay", "GET", bo.getBizParams());
            return new AliPrepayResponseBO().setType(AliPaymentsTypeEnum.H5_PE).setH5PeWith(pageRedirectionData);
        } catch (Exception e) {
            log.error("支付宝预支付错误(H5_PE)",e);
            throw new CommonException("Alipay payment failed");
        }
    }
    /** 支付订单号查询订单 */
    public static AliQueryOrderResponseBO queryOrderById(AliAppPayConfig payConfig, @NotBlank String orderId) {
        ApiClient apiClient = payConfig.getApiClient();
        AlipayTradeApi api = new AlipayTradeApi(apiClient);
        AlipayTradeQueryModel data = new AlipayTradeQueryModel();
        // 设置订单支付时传入的商户订单号
        data.setOutTradeNo(orderId);

        try {
            AlipayTradeQueryResponseModel response = api.query(data);
            return new AliQueryOrderResponseBO().setType(AliPaymentsTypeEnum.H5_PE).setQueryResponse(response);
        } catch (Exception e) {
            log.error("支付宝查询交易(H5_PE)",e);
            throw new CommonException("Order query failed");
        }
    }

}
