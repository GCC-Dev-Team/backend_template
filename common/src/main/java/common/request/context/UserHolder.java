package common.request.context;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author I Nhrl
 */

@SuppressWarnings("unused")
@Component
public class UserHolder {
    static UserHolderTypeBase userHolderTypeBase;
    private static void setUserHolderType(UserHolderTypeBase userHolderTypeBase){
        UserHolder.userHolderTypeBase = userHolderTypeBase;
    }
    @Autowired
    public void setUserHolderTypeConfig(UserHolderTypeBase userHolderTypeBase){
        setUserHolderType(userHolderTypeBase);
    }

    private static final ThreadLocal<String> DE = new ThreadLocal<>();

    public static<T extends BaseUserCommon> void saveUser(T user){
        userHolderTypeBase.saveUser(user);
    }

    /**
     * 获取用户上下文
     * 获取对象需要支持clone
     * @return T extends BaseUserCommon
     */
    public static<T extends BaseUserCommon> T getUser(){
        return ObjectUtil.clone(userHolderTypeBase.getUser());
    }
    public static void removeUser(){
        userHolderTypeBase.removeUser();
    }

    public static String getDevice(){
        return DE.get();
    }

    public static void saveDevice(String device){
        if (StrUtil.isNotBlank(device)) {
            DE.set(device);
        }else {
            throw new CommonException("The User-Agent information cannot be parsed");
        }
    }

    public static void removeDevice(){
        if (DE.get() != null) {
            DE.remove();
        }
    }
}
