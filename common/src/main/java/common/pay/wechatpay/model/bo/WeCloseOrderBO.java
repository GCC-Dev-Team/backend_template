package common.pay.wechatpay.model.bo;


import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



/**
 * 微信订单查询
 * @author I Nhrl
 */
@Data
public class WeCloseOrderBO {
    /**
     * 调用类型
     */
    @NotNull
    private WePaymentsTypeEnum type;
    /**
     * 订单id(商户订单id)
     */
    @NotBlank
    private String outTradeNo;
}
