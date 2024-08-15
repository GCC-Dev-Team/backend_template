package common.enums;


import common.exception.BaseErrorInterface;

/**
 * 公用异常
 * @author I Nhrl
 */
public enum CommonExceptionEnum implements BaseErrorInterface {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),
    //异常定义
    /**
     * 请求数据格式错误
     */
    BODY_NOT_MATCH(400, "The requested data format is incorrect"),
    /**
     * 频繁重复提交
     */
    NO_REPEAT_SUBMIT(4001, "Repeated submissions are too frequent"),
    /**
     * 用户认证失败
     */
    AUTHENTICATION_FAILED(401, "User authentication failure"),
    /**
     * 请求头错误
     */
    HEADER_VALIDATE_FAILED(400, "Header error"),
    /**
     * 认证信息已过期
     */
    EXPIRED_AUTHENTICATION(402, "The authentication information has expired"),
    /**
     * 无权限、拒绝访问
     */
    NOT_PERMISSIONS(403, "access denied"),
    /**
     * 未找到该资源
     */
    NOT_FOUND(404, "The resource was not found"),
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "server error"),
    /**
     * 请求数据错误
     */
    REQUEST_DATA_ERROR(400,"Request data error"),
    /**
     * 重复提交错误
     */
    NO_REPEAT_SUBMIT_ERROR(400, "Repeat commit error"),
    /**
     * 服务器正忙，请稍后重试
     */
    SERVER_BUSY(503, "The server is busy, please try again later"),
    /**
     * 账号或密码错误
     */
    LOGIN_USERNAME_PASSWORD_ERROR(401, "Account or password error"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(400,"The user does not exist."),
    /**
     * 邮箱格式异常
     */
    EMAIL_FORMAT_ERROR(400, "Email format error"),
    /**
     * 未知类型
     */
    TYPE_UNKNOWN(400, "Type unknown")
    ;

    /**
     * 错误码
     */
    private final int resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    CommonExceptionEnum(int resultCode, String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
