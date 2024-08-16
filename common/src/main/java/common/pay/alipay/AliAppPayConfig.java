package common.pay.alipay;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;


import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.Configuration;
import com.alipay.v3.util.model.AlipayConfig;
import common.exception.CommonException;
import lombok.Data;

/**
 * 支付宝支付配置
 *
 * @author I Nhrl
 */
@Data
public class AliAppPayConfig {

    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String appCert;
    private String alipayPublicCert;
    private String rootCert;
    private String encryptKey;
    private ApiClient apiClient;

    public AliAppPayConfig(JSONObject aliPay){
        if (aliPay==null||aliPay.get("appId")==null) {
            return;
        }
        setAppId(aliPay.getStr("appId"));
        setPrivateKey(aliPay.getStr("privateKey"));
        setAlipayPublicKey(aliPay.getStr("alipayPublicKey"));
        setAppCert(aliPay.getStr("appCert"));
        setAlipayPublicCert(aliPay.getStr("alipayPublicCert"));
        setRootCert(aliPay.getStr("rootCert"));
        setEncryptKey(aliPay.getStr("encryptKey"));

        AlipayConfig config = new AlipayConfig();
        config.setAppId(this.getAppId());
        config.setPrivateKey(this.getPrivateKey());
        config.setEncryptKey(this.getEncryptKey());

        if (StrUtil.isBlank(this.getAlipayPublicKey())) {
            //证书模式
            config.setAppCertContent(this.getAppCert());
            config.setAlipayPublicCertContent(this.getAlipayPublicCert());
            config.setRootCertContent(this.getRootCert());
        } else {
            //密钥模式
            config.setAlipayPublicKey(this.getAlipayPublicKey());
        }

        try {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath("https://openapi.alipay.com");
            defaultClient.setAlipayConfig(config);
            this.setApiClient(defaultClient);
        } catch (ApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
