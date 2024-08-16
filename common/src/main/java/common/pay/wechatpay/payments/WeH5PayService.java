package common.pay.wechatpay.payments;


import com.wechat.pay.java.service.payments.h5.H5Service;
import com.wechat.pay.java.service.payments.h5.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import common.pay.wechatpay.WeChatPayConfig;
import common.pay.wechatpay.model.bo.WePrepayResponseBO;
import common.pay.wechatpay.model.bo.WePrepayUnifiedBO;
import common.pay.wechatpay.model.bo.WeQueryOrderResponseBO;

import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;


/**
 * 微信 H5方式服务
 * @author I Nhrl
 */
public class WeH5PayService {
    /** 关闭订单 */
    public static void closeOrder(WeChatPayConfig payConfig, @NotBlank String outTradeNo) {
        H5Service service = new H5Service.Builder().config(payConfig.getWxPayConfig()).build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(outTradeNo);
        service.closeOrder(request);
    }
    /** H5支付下单 */
    public static WePrepayResponseBO prepay(WeChatPayConfig payConfig, WePrepayUnifiedBO bo) {
        H5Service service = new H5Service.Builder().config(payConfig.getWxPayConfig()).build();

        PrepayRequest request = bo.getH5Prepay();
        request.setAppid(payConfig.getAppId());
        request.setMchid(payConfig.getMerchantId());
        PrepayResponse prepayResponse = service.prepay(request);

        return new WePrepayResponseBO().setType(WePaymentsTypeEnum.H5).setH5With(prepayResponse);
    }
    /** 微信支付订单号查询订单 */
    public static WeQueryOrderResponseBO queryOrderById(WeChatPayConfig payConfig, @NotBlank String orderId) {
        H5Service service = new H5Service.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setTransactionId(orderId);
        Transaction transaction = service.queryOrderById(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.H5).setTransaction(transaction);
    }
    /** 商户订单号查询订单 */
    public static WeQueryOrderResponseBO queryOrderByOutTradeNo(WeChatPayConfig payConfig, @NotBlank String orderId) {
        H5Service service = new H5Service.Builder().config(payConfig.getWxPayConfig()).build();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(payConfig.getMerchantId());
        request.setOutTradeNo(orderId);
        Transaction transaction = service.queryOrderByOutTradeNo(request);

        return new WeQueryOrderResponseBO().setType(WePaymentsTypeEnum.H5).setTransaction(transaction);
    }
}
