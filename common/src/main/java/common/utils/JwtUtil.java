package common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author I Nhrl
 */
public class JwtUtil {
    public static final String USER_ID ="userId";
    public static final String TIME ="time";
    public static final String DEVICE ="device";
    public static final String LOGO ="logo";
    /**
     * 带上userId生成token
     * @param userId 用户标识
     * @param time 创建时间
     * @param logo 自定义标识
     * @param device 登录设备
     * @param secret 加密值
     * @return token
     */
    public static String createToken(String userId , String time, String logo, String device, String secret) {
        return JWT.create().withAudience(userId)
                .withIssuedAt(new Date())
                .withClaim(USER_ID,userId)
                .withClaim(TIME, time)
                .withClaim(DEVICE, device)
                .withClaim(LOGO,logo)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 从令牌中获取数据声明
     * @param token 令牌
     * @param secret 加密值
     */
    public static DecodedJWT getClaimsFromToken(String token, String secret) {
        DecodedJWT claims;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            claims = JWT.decode(token);
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
