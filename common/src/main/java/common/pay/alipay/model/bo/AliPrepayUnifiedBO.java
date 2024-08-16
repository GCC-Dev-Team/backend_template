package common.pay.alipay.model.bo;

import cn.hutool.core.util.StrUtil;

import common.pay.alipay.model.enums.AliPaymentsTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝统一下单
 * @author I Nhrl
 */
@Data
@Accessors(chain = true)
public class AliPrepayUnifiedBO {
    /**
     * 调用类型
     */
    private AliPaymentsTypeEnum type;
    /**
     * 支付参数
     */
    private Map<String, Object> bizParams;

    /**
     * 手机网页支付参数
     * @param orderNo 订单号
     * @param payPrice 支付金额(元|小数点后两位)
     * @param productName 产品名称
     * @param notifyUrl 回调地址
     * @param redirectUri 支付后网站返回地址
     */
    public void createH5PePrepay(String orderNo, BigDecimal payPrice, String productName, String notifyUrl, String redirectUri) {
        // 构造请求参数以调用接口
        Map<String, Object> bizParam = new HashMap<>();
        Map<String, Object> bizContent = new HashMap<>();

        if (StrUtil.isNotBlank(redirectUri)) {
            // 设置用户付款中途退出返回商户网站的地址
            bizParam.put("quit_url", redirectUri);
            // 设置用户付款后返回商户网站的地址
            bizParam.put("return_url", redirectUri);
        }

        // 设置商户订单号
        bizContent.put("out_trade_no", orderNo);
        // 设置订单总金额
        bizContent.put("total_amount", payPrice);
        // 设置订单标题
        bizContent.put("subject", productName);
        // 设置产品码
        bizContent.put("product_code", "QUICK_WAP_WAY");
        // 设置通知回调地址
        bizParam.put("notify_url", notifyUrl);
        bizParam.put("biz_content", bizContent);
        setBizParams(bizParam);
    }
}
