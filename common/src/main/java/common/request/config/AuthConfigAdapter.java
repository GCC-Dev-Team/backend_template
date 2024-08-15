package common.request.config;

import java.util.List;

/**
 * 实现该接口之后，修改需要授权登陆的路径，不需要授权登陆的路径
 * @author I Nhrl
 */
public interface AuthConfigAdapter {
    /**
     * 也许需要登录才可用的url
     */
    String MAYBE_AUTH_URI = "/auth/base/**";

    /**
     * 需要授权登陆的路径
     * @return 需要授权登陆的路径列表
     */
    List<String> pathPatterns();

    /**
     * 不需要授权登陆的路径
     * @return 不需要授权登陆的路径列表
     */
    List<String> excludePathPatterns();

    /**
     * 超级管理员可操作的路径
     * @return 路径列表
     */
    List<String> excludePathPatternsSuperAdmin();
}
