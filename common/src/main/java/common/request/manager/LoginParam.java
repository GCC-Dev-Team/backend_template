//package common.request.manager;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * @author I Nhrl
// */
//@Component
//public class LoginParam {
//    /**
//     * token加密
//     */
//    private static String secret;
//    /**
//     * 用户信息缓存时间 单位S
//     */
//    private static Long expiration;
//    /**
//     * 刷新时间（自动刷新间隔时间/刷新键缓存时间(用户活跃前提下)） 单位S (值需要小于expiration)
//     */
//    private static Long refresh;
//
//    @Value("${jwt-x.secret}")
//    public void setSecretConfig(String secret){
//        LoginParam.setSecret(secret);
//    }
//    @Value("${jwt-x.expiration}")
//    public void setExpirationConfig(Long expiration){
//        LoginParam.setExpiration(expiration);
//    }
//    @Value("${jwt-x.refresh}")
//    public void setRefreshConfig(Long refresh){
//        LoginParam.setRefresh(refresh);
//    }
//
//    public static String getSecret() {
//        return secret;
//    }
//
//    public static void setSecret(String secret) {
//        LoginParam.secret = secret;
//    }
//
//    public static Long getExpiration() {
//        return expiration;
//    }
//
//    public static void setExpiration(Long expiration) {
//        LoginParam.expiration = expiration;
//    }
//
//    public static Long getRefresh() {
//        return refresh;
//    }
//
//    public static void setRefresh(Long refresh) {
//        LoginParam.refresh = refresh;
//    }
//}
