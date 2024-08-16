package common.pay.wechatpay.model.enums;

/**
 * 类型
 *
 * @author I Nhrl
 */
public enum WePaymentsTypeEnum {
    /**
     *
     */
    APP("APP"),
    H5("H5"),
    JSAPI("JSAPI"),
    NATIVE("NATIVE")
    ;
    public final String value;

    WePaymentsTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
