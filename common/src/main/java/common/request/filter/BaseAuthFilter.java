package common.request.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;

import common.enums.CommonExceptionEnum;
import common.exception.CommonException;
import common.request.config.AuthConfigAdapter;
import common.request.context.BaseUserCommon;
import common.request.context.UserHolder;
import common.request.handler.HttpHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;


import java.io.IOException;
import java.util.List;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可：
 *
 * @author I Nhrl
 */

@SuppressWarnings("unused")
@Slf4j
public abstract class BaseAuthFilter implements Filter {
    @Resource
    private HttpHandler httpHandler;
    protected final AuthConfigAdapter authConfigAdapter;

    protected BaseAuthFilter(AuthConfigAdapter authConfigAdapter) {
        this.authConfigAdapter = authConfigAdapter;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            preHandle(req, resp);

            BaseUserCommon user = UserHolder.getUser();
            String requestUri = req.getRequestURI();
            List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();
            AntPathMatcher pathMatcher = new AntPathMatcher();

            // 如果匹配不需要授权的路径，就不需要校验是否需要授权
            if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
                for (String excludePathPattern : excludePathPatterns) {
                    if (pathMatcher.match(excludePathPattern, requestUri)) {
                        chain.doFilter(req, resp);
                        return;
                    }
                }
            }
            if (middleHandle(user,chain,pathMatcher,requestUri,request,response)){
                chain.doFilter(req, resp);
                return;
            }

            // 如果有token，就要获取token
            if (user != null && ObjectUtil.isNotEmpty(user.getUserId())) {
                chain.doFilter(req, resp);
            }
            // 也许需要登录，不登陆也能用的uri
            else if (!pathMatcher.match(AuthConfigAdapter.MAYBE_AUTH_URI, requestUri)) {
                // 返回前端401
                log.error("uri:{}", requestUri);
                httpHandler.printServerResponseToWeb(CommonExceptionEnum.AUTHENTICATION_FAILED.getResultMsg(),
                        CommonExceptionEnum.AUTHENTICATION_FAILED.getResultCode());
            }
        } catch (CommonException e){
            httpHandler.printServerResponseToWeb(e.getMessage(), e.getErrorCode());
        }catch (Exception e) {
            httpHandler.printServerResponseToWeb(e.getMessage(), 500);
        } finally {
            UserHolder.removeUser();
            UserHolder.removeDevice();
            finallyHandle();
        }
    }

    /**
     * 前置处理
     * @param request req
     * @param response resp
     */
    protected abstract void preHandle(HttpServletRequest request, HttpServletResponse response);

    /**
     * 中间处理
     * @param t UserHolder.getUser()
     * @param chain filter
     * @param pathMatcher path
     * @param requestUri uri
     * @param request req
     * @param response resp
     * @return 是否结束
     * @throws ServletException Ex
     * @throws IOException Ex
     */
    protected abstract boolean middleHandle(BaseUserCommon t, FilterChain chain,AntPathMatcher pathMatcher,String requestUri,ServletRequest request, ServletResponse response) throws ServletException, IOException;

    /**
     * 结束处理
     */
    protected abstract void finallyHandle();
}
