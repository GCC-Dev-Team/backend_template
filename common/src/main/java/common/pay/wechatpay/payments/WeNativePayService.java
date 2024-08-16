package common.pay.wechatpay.payments;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import common.pay.wechatpay.WeChatPayConfig;
import common.pay.wechatpay.model.bo.WePrepayResponseBO;
import common.pay.wechatpay.model.bo.WePrepayUnifiedBO;
import common.pay.wechatpay.model.bo.WeQueryOrderResponseBO;
import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;


/**
 * 微信 Native方式服务
 * @author I Nhrl
 */
public class WeNativePayService {
    /** 关闭订单 */
    public static void closeOrder(WeChatPayConfig payConfig, @NotBlank String outTradeNo) {
        NativePayService service = new NativePayService.Builder().config(payConfig.getWxPayConfig()).build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(outTradeNo);
        service.closeOrder(request);
    }
    /** Native支付预下单 */
    public static WePrepayResponseBO prepay(WeChatPayConfig payConfig, WePrepayUnifiedBO bo) {
        NativePayService service = new NativePayService.Builder().config(payConfig.getWxPayConfig()).build();

        PrepayRequest request = bo.getNativePrepay();
        request.setAppid(payConfig.getAppId());
        request.setMchid(payConfig.getMerchantId());
        PrepayResponse prepayResponse = service.prepay(request);

        return new WePrepayResponseBO().setType(WePaymentsTypeEnum.NATIVE).setNativeWith(prepayResponse);
    }
    /** 微信支付订单号查询订单 */
    public static WeQueryOrderResponseBO queryOrderById(WeChatPayConfig payConfig, @NotBlank String orderId) {
        NativePayService service = new NativePayService.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setTransactionId(orderId);
        Transaction transaction = service.queryOrderById(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.NATIVE).setTransaction(transaction);
    }
    /** 商户订单号查询订单 */
    public static WeQueryOrderResponseBO queryOrderByOutTradeNo(WeChatPayConfig payConfig, @NotBlank String orderId) {
        NativePayService service = new NativePayService.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(orderId);
        Transaction transaction = service.queryOrderByOutTradeNo(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.NATIVE).setTransaction(transaction);
    }
}
