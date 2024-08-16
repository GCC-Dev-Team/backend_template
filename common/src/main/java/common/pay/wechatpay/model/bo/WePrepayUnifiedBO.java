package common.pay.wechatpay.model.bo;


import com.wechat.pay.java.service.payments.h5.model.Amount;
import com.wechat.pay.java.service.payments.h5.model.H5Info;
import com.wechat.pay.java.service.payments.h5.model.PrepayRequest;
import com.wechat.pay.java.service.payments.h5.model.SceneInfo;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import lombok.Data;

/**
 * 微信预支付统一下单 业务对象
 * <p>自动添入: appId、merchantId</p>
 * @author I Nhrl
 */
@Data
public class WePrepayUnifiedBO {
    /**
     * 调用类型
     */
    private WePaymentsTypeEnum type;
    /**
     * WePaymentsTypeEnum.APP支付参数<p/>
     * <a href="https://pay.weixin.qq.com/docs/merchant/apis/in-app-payment/direct-jsons/app-prepay.html">APP</a>
     */
    private com.wechat.pay.java.service.payments.app.model.PrepayRequest appPrepay;
    /**
     * WePaymentsTypeEnum.H5支付参数<p/>
     * <a href="https://pay.weixin.qq.com/docs/merchant/apis/h5-payment/direct-jsons/h5-prepay.html">H5</a>
     */
    private com.wechat.pay.java.service.payments.h5.model.PrepayRequest h5Prepay;
    /**
     * WePaymentsTypeEnum.JSAPI支付参数<p/>
     * <a href="https://pay.weixin.qq.com/docs/merchant/apis/jsapi-payment/direct-jsons/jsapi-prepay.html">JSAPI</a>
     */
    private com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest jsapiPrepay;
    /**
     * WePaymentsTypeEnum.NATIVE支付参数<p/>
     * <a href="https://pay.weixin.qq.com/docs/merchant/apis/native-payment/direct-jsons/native-prepay.html">NATIVE</a>
     */
    private com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest nativePrepay;

    public void createH5Prepay(int payPrice, String device, String productName, String ipAddr, String notifyUrl, String productDescribe, String orderNo) {
        Amount amount = new Amount();
        amount.setTotal(payPrice);

        H5Info h5Info = new H5Info();
        h5Info.setType(device);
        h5Info.setAppName(productName);
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp(ipAddr);
        sceneInfo.setH5Info(h5Info);

        PrepayRequest h5PrepayRequest = new PrepayRequest();
        h5PrepayRequest.setDescription(productDescribe);
        h5PrepayRequest.setOutTradeNo(orderNo);
        h5PrepayRequest.setNotifyUrl(notifyUrl);
        h5PrepayRequest.setAmount(amount);
        h5PrepayRequest.setSceneInfo(sceneInfo);
        this.setH5Prepay(h5PrepayRequest);
    }

    public void createJsPrepay(String openId, int payPrice, String ipAddr, String notifyUrl, String productDescribe, String orderNo) {
        com.wechat.pay.java.service.payments.jsapi.model.Amount amount = new com.wechat.pay.java.service.payments.jsapi.model.Amount();
        amount.setTotal(payPrice);

        com.wechat.pay.java.service.payments.jsapi.model.SceneInfo sceneInfo = new com.wechat.pay.java.service.payments.jsapi.model.SceneInfo();
        sceneInfo.setPayerClientIp(ipAddr);

        Payer payer = new Payer();
        payer.setOpenid(openId);

        com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest jsPrepayRequest = new com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest();
        jsPrepayRequest.setDescription(productDescribe);
        jsPrepayRequest.setOutTradeNo(orderNo);
        jsPrepayRequest.setNotifyUrl(notifyUrl);
        jsPrepayRequest.setAmount(amount);
        jsPrepayRequest.setSceneInfo(sceneInfo);
        jsPrepayRequest.setPayer(payer);
        this.setJsapiPrepay(jsPrepayRequest);

    }
}
