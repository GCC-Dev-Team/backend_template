//package miniapp.filter;
//
//import common.request.config.AuthConfigAdapter;
//import common.request.context.BaseUserCommon;
//import common.request.filter.BaseAuthFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import java.io.IOException;
//@Component
//public class MiniAppFilter extends BaseAuthFilter{
//
//    protected MiniAppFilter(AuthConfigAdapter authConfigAdapter) {
//        super(authConfigAdapter);
//    }
//
//    @Override
//    protected void preHandle(HttpServletRequest request, HttpServletResponse response) {
//
//    }
//
//    @Override
//    protected boolean middleHandle(BaseUserCommon t, FilterChain chain, AntPathMatcher pathMatcher, String requestUri, ServletRequest request, ServletResponse response) throws ServletException, IOException {
//        return true;
//    }
//
//    @Override
//    protected void finallyHandle() {
//
//    }
//}
