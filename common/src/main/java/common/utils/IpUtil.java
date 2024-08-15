package common.utils;



import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * IP 工具
 * @author I Nhrl
 */
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static  final List<String> IP_HEAD_LIST = List.of("X-Forwarded-For",
            "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "X-Real-IP");

    /**
     * 得到用户的真实地址,如果有多个就取第一个
     * @return IP
     */
    public static String getIpAddr() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        for (String ipHead : IP_HEAD_LIST) {
            if (checkIp(request.getHeader(ipHead))) {
                return request.getHeader(ipHead).split(",")[0].trim();
            }
        }
        return "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) ? "127.0.0.1" : request.getRemoteAddr();
    }
    /**
     * 检查ip存在
     */
    private static boolean checkIp(String ip) {
        return !(null == ip || 0 == ip.length() || UNKNOWN.equalsIgnoreCase(ip));
    }
}
