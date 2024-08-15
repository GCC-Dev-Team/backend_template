package common.request.handler;


import common.enums.CommonExceptionEnum;
import common.exception.BaseErrorInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author I Nhrl
 */
@Getter
@NoArgsConstructor
public class WebResponse<T> {

    /**
     * 响应代码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    @Nullable
    private T result;

    public WebResponse(BaseErrorInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    public WebResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebResponse(int code, String message,@Nullable T data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }

    /**
     * 成功
     */
    public static <T> WebResponse<T> success() {
        return success(null);
    }

    /**
     * 成功
     */
    public static <T> WebResponse<T> success(int code, String message,@Nullable T data) {
        return new WebResponse<>(code,message,data);
    }

    /**
     * 成功
     */
    public static <T> WebResponse<T> success(@Nullable T data) {
        return new WebResponse<>(CommonExceptionEnum.SUCCESS.getResultCode(),CommonExceptionEnum.SUCCESS.getResultMsg(),data);
    }

    /**
     * 失败
     */
    public static WebResponse<Object> error(BaseErrorInterface errorInfo) {
        return new WebResponse<>(errorInfo);
    }

    /**
     * 失败
     */
    public static <T> WebResponse<T> error(int code, String message) {
        return new WebResponse<>(code,message);
    }

    /**
     * 失败
     */
    public static <T> WebResponse<T> error(String message) {
        return new WebResponse<>(400,message);
    }
}
