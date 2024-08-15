package common.request.context;

import cn.hutool.core.convert.Convert;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 配置用户上下文类型
 * @author I Nhrl
 */
@Component
public abstract class UserHolderTypeBase {
    final Class<? extends BaseUserCommon> clazz;

    private final ThreadLocal<? extends BaseUserCommon> local = new ThreadLocal<>();

    protected UserHolderTypeBase(Class<? extends BaseUserCommon> clazz){
        this.clazz = clazz;
    }

    public <T extends BaseUserCommon> void saveUser(T user){
        local.set(Convert.convert((Type) clazz,user));
    }

    public <T extends BaseUserCommon> T getUser(){
        return Convert.convert((Type) clazz,local.get());
    }

    public void removeUser(){
        if (local.get() != null) {
            local.remove();
        }
    }

}
