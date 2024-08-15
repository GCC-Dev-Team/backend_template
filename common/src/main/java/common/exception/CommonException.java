package common.exception;

import lombok.Getter;

/**
 * 自定义异常
 *
 * @author I Nhrl
 */
@Getter
public class CommonException extends RuntimeException {

    private final int errorCode;

    private final String errorMessage;

    public CommonException(BaseErrorInterface baseErrorInterface) {
        super(baseErrorInterface.getResultMsg());
        this.errorCode = baseErrorInterface.getResultCode();
        this.errorMessage = baseErrorInterface.getResultMsg();
    }

    public CommonException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(String errorMessage) {
        super(errorMessage);
        this.errorCode = 500;
        this.errorMessage = errorMessage;
    }
}
