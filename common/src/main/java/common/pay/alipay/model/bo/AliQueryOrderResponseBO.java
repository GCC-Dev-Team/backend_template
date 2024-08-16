package common.pay.alipay.model.bo;


import com.alipay.v3.model.AlipayTradeQueryResponseModel;
import common.pay.alipay.model.enums.AliPaymentsTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付宝订单查询信息
 * @author I Nhrl
 */
@Data
@Accessors(chain = true)
public class AliQueryOrderResponseBO {
    /**
     * 调用类型
     */
    private AliPaymentsTypeEnum type;
    /**
     * 订单信息
     */
    private AlipayTradeQueryResponseModel queryResponse;
}
