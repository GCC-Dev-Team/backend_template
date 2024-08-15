package common.request.handler;

import cn.hutool.core.exceptions.ValidateException;

import common.enums.CommonExceptionEnum;
import common.exception.CommonException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

/**
 * @author I Nhrl
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求异常
     */
    @ExceptionHandler(value = ServletException.class)
    @ResponseBody
    public WebResponse<Object> servletExceptionHandler(HttpServletResponse response) {
//        response.setStatus(403);
        return WebResponse.error(403,null);
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public WebResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                        HttpServletResponse response) {
        log.error("缺少请求参数{}", e.getMessage());
//        response.setStatus(400);
        return WebResponse.error(400,"required_parameter_is_not_present");
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public WebResponse<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                HttpServletResponse response) {
        log.error("参数解析失败{}", e.getMessage());
//        response.setStatus(400);
        return WebResponse.error(400,"could_not_read_json");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public WebResponse<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
                                                           HttpServletResponse response) {
        log.error("请求参数类型错误{}", e.getMessage());
//        response.setStatus(400);
        return WebResponse.error(400,"Parameter type error");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public WebResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                       HttpServletResponse response) {
        log.error("不支持当前请求方法{}", e.getMessage());
//        response.setStatus(405);
        return WebResponse.error(405,"request_method_not_supported");
    }

    /**
     * 处理自定义的业务异常
     * @param e 异常
     */
    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public WebResponse<Object> customExceptionHandler(CommonException e, HttpServletResponse response) {
        log.error(e.getErrorMessage());
//        response.setStatus(e.getErrorCode());
        return WebResponse.error(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 处理自定义的校验异常
     * @param e 异常
     */
    @ExceptionHandler(value = ValidateException.class)
    @ResponseBody
    public WebResponse<Object> validateExceptionHandler(ValidateException e, HttpServletResponse response) {
        log.error(e.getMessage());
//        response.setStatus(400);
        return WebResponse.error(e.getStatus(), e.getMessage());
    }

    /**
     * 处理自定义的校验异常
     * @param e 异常
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public WebResponse<Object> bindExceptionHandler(BindException e, HttpServletResponse response) {
        log.error(e.getMessage());
//        response.setStatus(400);
        String defaultMessage = e.getFieldError()==null?"Parameter verification exception":e.getFieldError().getDefaultMessage();
        return WebResponse.error(400, defaultMessage);
    }

    /**
     * 运行时异常异常
     * @param e 异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public WebResponse<Object> innerExceptionHandler(RuntimeException e, HttpServletResponse response) {
        log.error(getExceptionInfo(e));
//        response.setStatus(500);
        return WebResponse.error(CommonExceptionEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 安全异常
     */
    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    @ResponseBody
    public WebResponse<Object> accessDeniedExceptionHandler(HttpServletResponse response) {
//        response.setStatus(403);
        return WebResponse.error(CommonExceptionEnum.NOT_PERMISSIONS);
    }

    /**
     * 获取异常信息
     * @param ex 异常
     */
    private static String getExceptionInfo(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        ex.printStackTrace(printStream);
        String rs = out.toString();
        try {
            printStream.close();
            out.close();
        } catch (Exception e) {
            log.error("",e);
        }
        return rs;
    }

    /**
     *  校验错误拦截处理
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public WebResponse<Object> validationBodyException(MethodArgumentNotValidException exception, HttpServletResponse response){
        BindingResult result = exception.getBindingResult();
          if (result.hasErrors()) {
              List<ObjectError> errors = result.getAllErrors();
              errors.forEach(p ->{
                  FieldError fieldError = (FieldError) p;
                  log.error("Data check failure : object:{},field:{},errorMessage:{}",fieldError.getObjectName(),fieldError.getField(),fieldError.getDefaultMessage());
              });
          }
//        response.setStatus(400);
        return WebResponse.error(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
    }

}
