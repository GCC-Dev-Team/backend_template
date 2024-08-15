package common.request.manager;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;

import common.enums.CommonExceptionEnum;
import common.exception.CommonException;
import common.utils.IpUtil;
import common.utils.cache.CaCheContext;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author I Nhrl
 */
@Component
@RequiredArgsConstructor
public class PasswordCheckManager {
    final CaCheContext caCheContext;
    /**
     * 半小时内最多错误*次
     */
    private static Integer timesCheckNum;
    /**
     * 错误时间达标 冻结时长(min)
     */
    private static Integer timesCheckLimitLogin;
    /**
     * 登录盐
     */
    private static String salt;

    @Value("${login.num}")
    public void setTimesCheckNumConfig(Integer timesCheckNum) {
        PasswordCheckManager.setTimesCheckNum(timesCheckNum);
    }

    @Value("${login.limit}")
    public void setTimesCheckLimitLoginConfig(Integer timesCheckLimitLogin) {
        PasswordCheckManager.setTimesCheckLimitLogin(timesCheckLimitLogin);
    }

    @Value("${login.salt}")
    public void setSaltConfig(String salt) {
        PasswordCheckManager.setSalt(salt);
    }

    /**
     * 检查用户输入错误的验证码次数
     */
    private static final String CHECK_VALID_CODE_NUM_PREFIX = "checkUserInputErrorPassword_";

    public void checkPassword(String userNameOrMobile, String password, String verifyPassword) {

        String checkPrefix = CHECK_VALID_CODE_NUM_PREFIX + IpUtil.getIpAddr() + StrUtil.COLON + userNameOrMobile;

        Integer count = caCheContext.getCacheObject(checkPrefix, new TypeReference<>() {
        });
        if (count == null) {
            count = 0;
        }
        if (count > timesCheckNum) {
            throw new CommonException(401, "Password error [" + timesCheckNum + "] times, limited login [" + timesCheckLimitLogin + "] minutes.");
        }
        //检验密码是否正确
        // 密码不正确
        if (Boolean.FALSE.equals(PasswordManager.comparePassword(password, verifyPassword, salt))) {
            count++;
            Integer num = 30;
            if (count > timesCheckNum) {
                num = timesCheckLimitLogin;
            }
            // 失效
            caCheContext.setCacheObject(checkPrefix, count, 60L * num);
            throw new CommonException(CommonExceptionEnum.LOGIN_USERNAME_PASSWORD_ERROR);
        } else {
           caCheContext.del(checkPrefix);
        }
    }


    public static String getSalt() {
        return salt;
    }

    public static void setSalt(String salt) {
        PasswordCheckManager.salt = salt;
    }

    public static void setTimesCheckLimitLogin(Integer timesCheckLimitLogin) {
        PasswordCheckManager.timesCheckLimitLogin = timesCheckLimitLogin;
    }

    public static void setTimesCheckNum(Integer timesCheckNum) {
        PasswordCheckManager.timesCheckNum = timesCheckNum;
    }
}
