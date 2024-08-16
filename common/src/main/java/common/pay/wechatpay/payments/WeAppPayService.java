package common.pay.wechatpay.payments;


import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.app.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import common.pay.wechatpay.WeChatPayConfig;
import common.pay.wechatpay.model.bo.WePrepayResponseBO;
import common.pay.wechatpay.model.bo.WePrepayUnifiedBO;
import common.pay.wechatpay.model.bo.WeQueryOrderResponseBO;
import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;


/**
 * 微信 APP方式支付服务
 *
 * @author I Nhrl
 */
public class WeAppPayService {
    /**
     * 关闭订单
     */
    public static void closeOrder(WeChatPayConfig payConfig, @NotBlank String outTradeNo) {
        AppServiceExtension service = new AppServiceExtension.Builder().config(payConfig.getWxPayConfig()).build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(outTradeNo);
        service.closeOrder(request);
    }

    /**
     * APP支付下单，并返回APP调起支付数据
     */
    public static WePrepayResponseBO prepayWithRequestPayment(WeChatPayConfig payConfig, WePrepayUnifiedBO bo) {
        AppServiceExtension service = new AppServiceExtension.Builder().config(payConfig.getWxPayConfig()).build();

        PrepayRequest request = bo.getAppPrepay();
        request.setAppid(payConfig.getAppId());
        request.setMchid(payConfig.getMerchantId());
        PrepayWithRequestPaymentResponse paymentResponse = service.prepayWithRequestPayment(request);

        return new WePrepayResponseBO().setType(WePaymentsTypeEnum.APP).setAppWith(paymentResponse);
    }

    /**
     * 微信支付订单号查询订单
     */
    public static WeQueryOrderResponseBO queryOrderById(WeChatPayConfig payConfig, @NotBlank String orderId) {
        AppServiceExtension service = new AppServiceExtension.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setTransactionId(orderId);
        Transaction transaction = service.queryOrderById(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.APP).setTransaction(transaction);
    }

    /**
     * 商户订单号查询订单
     */
    public static WeQueryOrderResponseBO queryOrderByOutTradeNo(WeChatPayConfig payConfig, @NotBlank String orderId) {
        AppServiceExtension service = new AppServiceExtension.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(orderId);
        Transaction transaction = service.queryOrderByOutTradeNo(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.APP).setTransaction(transaction);
    }
}
