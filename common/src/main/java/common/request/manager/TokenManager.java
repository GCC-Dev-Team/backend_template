//package common.request.manager;
//
//import cn.hutool.core.util.StrUtil;
//
//
///**
// * @author I Nhrl
// */
//public class TokenManager {
//    public static final String REFRESH_KEY = "refresh:";
//
//    /**
//     * 缓存用户信息并返回token
//     *
//     * @param userId    用户标识
//     * @param logo      自定义标识
//     * @param time      时间
//     * @param device    登录设备
//     * @param redisData 缓存数据
//     * @return token
//     */
//    public static String loginToken(String userId, String logo, long time, String device, Object redisData) {
//        String userKey = logo + StrUtil.COLON + userId;
//        String refreshKey = REFRESH_KEY + logo + StrUtil.COLON + userId;
//        CaffeineUtil.put(userKey, redisData, LoginParam.getExpiration());
//        CaffeineUtil.put(refreshKey, time, LoginParam.getRefresh());
//        return JwtUtil.createToken(userId, StrUtil.toString(time), logo, device, LoginParam.getSecret());
//    }
//
//    /**
//     * 删除/清除 用户刷新key,  刷新用token信息
//     *
//     * @param userId 用户id
//     * @param logo   标识
//     */
//    public static void refreshUserToken(String userId, String logo) {
//        CaffeineUtil.del(REFRESH_KEY + logo + StrUtil.COLON + userId);
//    }
//
//    /**
//     * 删除/清除 用户token,使其token失效
//     *
//     * @param userId 用户id
//     * @param logo   标识
//     */
//    public static void removeUserToken(String userId, String logo) {
//        CaffeineUtil.del(logo + StrUtil.COLON + userId);
//    }
//}
