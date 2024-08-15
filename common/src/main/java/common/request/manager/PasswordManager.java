package common.request.manager;

import cn.hutool.crypto.digest.DigestUtil;

import common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

/**
 * 判断密码
 * @author I Nhrl
 */
@Slf4j
public class PasswordManager {

    public static Boolean comparePassword(String data,String password,String salt) {
        boolean decryptPassword;
        try {
            decryptPassword = encryptionPassword(password, salt).equals(data);
        } catch (RuntimeException e) {
            log.error("Exception:", e);
            throw new CommonException(403,"Decryption error");
        }
        return decryptPassword;
    }

    public static String encryptionPassword(String password,String salt){
        return DigestUtil.md5Hex(DigestUtil.md5Hex(password) + salt);
    }
}
