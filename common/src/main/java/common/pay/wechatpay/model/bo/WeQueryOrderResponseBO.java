package common.pay.wechatpay.model.bo;


import com.wechat.pay.java.service.payments.model.Transaction;
import common.pay.wechatpay.model.enums.WePaymentsTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信查询订单响应信息
 * @author I Nhrl
 */
@Data
@Accessors(chain = true)
public class WeQueryOrderResponseBO {
    /**
     * 调用类型
     */
    private WePaymentsTypeEnum type;
    /**
     * 查询订单信息
     */
    private Transaction transaction;
}
