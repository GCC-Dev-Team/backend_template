package common.pay.wechatpay.model.bo;


import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信预支付回调
 * @author I Nhrl
 */
@Data
@Accessors(chain = true)
public class WePrepayResponseBO {
    /**
     * 调用类型
     */
    private WePaymentsTypeEnum type;
    /**
     * WePaymentsTypeEnum.APP预支付回调
     * 预支付交易会话标识：partnerId
     */
    private com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse appWith;
    /**
     * WePaymentsTypeEnum.H5预支付回调
     */
    private com.wechat.pay.java.service.payments.h5.model.PrepayResponse h5With;
    /**
     * WePaymentsTypeEnum.JSAPI预支付回调
     */
    private com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse jsapiWith;
    /**
     * WePaymentsTypeEnum.NATIVE预支付回调
     */
    private com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse nativeWith;


}
