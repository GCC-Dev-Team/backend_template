package common.request.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;

import common.exception.CommonException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author I Nhrl
 */
@Slf4j
@Component
public class HttpHandler {
    public void printServerResponseToWeb(String str, int status) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            log.error("requestAttributes is null, can not print to web");
            return;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        if (response == null) {
            log.error("httpServletResponse is null, can not print to web");
            return;
        }
        log.error("response error: " + str);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(status);
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            printWriter.write(JSONUtil.toJsonStr(new WebResponse<>(status,str)));
        }
        catch (IOException e) {
            throw new CommonException(500, "io error");
        }
    }
}
