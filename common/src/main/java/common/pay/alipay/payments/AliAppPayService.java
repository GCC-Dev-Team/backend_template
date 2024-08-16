package common.pay.alipay.payments;



import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.*;
import common.exception.CommonException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;



/**
 * 阿里APP方式支付服务
 *
 * @author I Nhrl
 */
@Slf4j
public class AliAppPayService {
    /**
     * 关闭订单
     */
    public static void closeOrder(@NotBlank String outTradeNo) {
    }

    /**
     * APP支付下单，并返回APP调起支付数据
     */
    public static AlipayTradePayResponseModel prepayWithRequestPayment(AlipayTradePayModel payModel) {
        AlipayTradeApi api = new AlipayTradeApi();
        try {
            return api.pay(payModel);
        } catch (ApiException e) {
            AlipayTradePayDefaultResponse errorObject = (AlipayTradePayDefaultResponse) e.getErrorObject();
            if (errorObject != null && errorObject.getActualInstance() instanceof CommonErrorType) {
                //获取公共错误码
                CommonErrorType actualInstance = errorObject.getCommonErrorType();
                log.error("CommonErrorType: {}", actualInstance.toString());
            } else if (errorObject != null && errorObject.getActualInstance() instanceof AlipayTradePayErrorResponseModel) {
                //获取业务错误码
                AlipayTradePayErrorResponseModel actualInstance = errorObject.getAlipayTradePayErrorResponseModel();
                log.info("AlipayTradePayErrorResponseModel: {}", actualInstance.toString());
            } else {
                //获取其他报错（如加验签失败、http请求失败等）
                log.error("Status code: {}", e.getCode());
                log.error("Reason: {}", e.getResponseBody());
                log.error("Response headers: {}", e.getResponseHeaders());
            }
            throw new CommonException("Payment order failed");
        }
    }

    /**
     * 微信支付订单号查询订单
     */
    public static void queryOrderById(@NotBlank String orderId) {
    }

    /**
     * 商户订单号查询订单
     */
    public static void queryOrderByOutTradeNo(@NotBlank String orderId) {
    }
}
