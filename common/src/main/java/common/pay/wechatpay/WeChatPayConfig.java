package common.pay.wechatpay;

import cn.hutool.json.JSONObject;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信支付配置
 *
 * @author I Nhrl
 */
@Data
@NoArgsConstructor
public class WeChatPayConfig {
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 商户API私钥
     */
    private String privateKey;
    /**
     * 商户证书序列号
     */
    private String merchantSerialNumber;
    /**
     * 商户APIV3密钥
     */
    private String apiV3Key;
    /**
     * app id
     */
    private String appId;
    /**
     * 应用密钥
     */
    private String secret;

    private Config wxPayConfig;
    private NotificationConfig notificationConfig;

    /**
     * 微信配置初始化,先有config然后有server,最后调方法
     * @param weChatPay
     */
    public WeChatPayConfig(JSONObject weChatPay){
        setMerchantId(weChatPay.getStr("merchantId"));
        setPrivateKey(weChatPay.getStr("privateKey"));
        setMerchantSerialNumber(weChatPay.getStr("merchantSerialNumber"));
        setApiV3Key(weChatPay.getStr("apiV3Key"));
        setAppId(weChatPay.getStr("appId"));
        setSecret(weChatPay.getStr("secret"));

        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(getMerchantId())
                .privateKey(getPrivateKey())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
        setWxPayConfig(config);
        setNotificationConfig(config);
    }
}
