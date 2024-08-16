package common.pay.alipay.model.bo;


import common.pay.alipay.model.enums.AliPaymentsTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付宝预支付回调
 * @author I Nhrl
 */
@Data
@Accessors(chain = true)
public class AliPrepayResponseBO {
    /**
     * 调用类型
     */
    private AliPaymentsTypeEnum type;
    /**
     * AliPaymentsTypeEnum.H5_PE预支付回调(GET : 重定向URL)
     */
    private String h5PeWith;
}
