package common.request.context;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * base用户信息->实现此抽象定义模块专属用户上下文
 * @author I Nhrl
 */
@Data
public abstract class BaseUserCommon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 标识
     */
    protected String userId;
    /**
     * 是否管理员
     */
    protected Integer isAdmin;
    /**
     * 设备
     */
    protected String device;
    /**
     * 名称
     */
    protected String userName;
    /**
     * 登录ip
     */
    protected String loginIp;
    /**
     * 过期时间(毫秒)|(实际值为登录时间,用于比对系统当前时间)
     */
    protected Long expiresIn;

    public String logo(){
        return isAdmin + StrUtil.COLON + userId;
    }
}
