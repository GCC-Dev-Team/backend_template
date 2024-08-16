package common.pay.alipay.model.bo;


import common.pay.alipay.model.enums.AliPaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 支付宝 订单查询
 */
@Data
@Accessors(chain = true)
public class AliQueryOrderBO {
    /**
     * 调用类型
     */
    @NotNull
    private AliPaymentsTypeEnum type;
    /**
     * 订单编号
     */
    @NotBlank
    private String outTradeNo;
}
