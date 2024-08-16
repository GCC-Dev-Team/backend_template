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
public class WeQueryOrderBO {
    /**
     * 调用类型
     */
    @NotNull
    private WePaymentsTypeEnum type;
    /**
     * 订单id类型(true 微信订单id(微信生成的订单id)、 false 商户订单id(商户自定义的订单id))
     */
    @NotNull
    private Boolean idType;
    /**
     * 订单id
     */
    @NotBlank
    private String orderId;
}
