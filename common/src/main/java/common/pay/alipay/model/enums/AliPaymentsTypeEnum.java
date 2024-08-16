package common.pay.alipay.model.enums;

/**
 * 类型
 *
 * @author I Nhrl
 */
public enum AliPaymentsTypeEnum {
    /**
     *
     */
    APP("APP"),
    H5_PE("H5PE"),
    H5_PC("H5PC"),
    JSAPI("JSAPI")
    ;
    public final String value;

    AliPaymentsTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
