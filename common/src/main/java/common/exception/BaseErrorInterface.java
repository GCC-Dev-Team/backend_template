package common.exception;

/**
 * @author I Nhrl
 */
public interface BaseErrorInterface {
    /**
     * 错误码
     * @return 获取异常错误码
     */
    int getResultCode();

    /**
     * 错误描述
     * @return 获取异常错误描述
     */
    String getResultMsg();
}
