package com.thertno.xiaohuli.common.exception;


import com.thertno.xiaohuli.bean.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;


/**
 * @author chenchao
 * @version 1.0
 * @date 2016年3月23日 下午8:10:47
 * @throws
 * @see {http://www.jianshu.com/p/752b44fc70e7}
 * @see {http://docs.spring.io/spring-boot/docs/1.4.0.M1/reference/htmlsingle/#boot-features-spring-mvc}
 * You can also define a @ControllerAdvice to customize the JSON document to return for a
 * particular controller and/or exception type.
 */

@ControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @ExceptionHandler(value = {MissingServletRequestParameterException.class,
            ConstraintViolationException.class, TypeMismatchException.class, Throwable.class})
    @ResponseBody
    ResponseResult<Object> handleControllerException(Exception ex) throws IOException {
        int code = 1;
        String message = "";
        if (ex != null) {
            message = ex.getMessage();
        }

        ErrorStatus errorStatus = null;
        int errorCode = ErrorStatus.EC_BUSINESS_PARAMS_REQUIRED.code();
        if (ex instanceof ServiceException) {
            ServiceException se = (ServiceException) ex;
            errorStatus = se.getErrorStatus();
            if (errorStatus != null) {
                code = errorStatus.code();
            } else {
                code = se.getCode();
            }
            message = se.getMessage();
        } else if (ex instanceof MissingServletRequestParameterException) {  // 如果请求参数为必填但没有传则抛异常
            MissingServletRequestParameterException e =
                    (MissingServletRequestParameterException) ex;
            String parameterName = e.getParameterName();
            message = parameterName + " is required";
            code = ErrorStatus.EC_BUSINESS_PARAMS_REQUIRED.code();
        } else if (ex instanceof ConstraintViolationException) {
            // 参数规范约束异常
            ConstraintViolationException e = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            Optional<ConstraintViolation<?>> constrain = violations.stream().findFirst();
            if (constrain.isPresent()) {
                message = constrain.get().getMessage();
                code = errorCode;
            }
        } else if (ex instanceof TypeMismatchException) {
            // 参数类型不匹配异常
            TypeMismatchException e = (TypeMismatchException) ex;
            message = "parameter value " + e.getValue() + " is typeMismatch";
            code = errorCode;
        } else if (ex instanceof BindException) {
            // 对象参数绑定异常
            BindException e = (BindException) ex;
            message = e.getAllErrors().get(0).getDefaultMessage();
            code = errorCode;
        }
        if (ex instanceof ServiceException || ex instanceof MissingServletRequestParameterException
                || ex instanceof ConstraintViolationException || ex instanceof TypeMismatchException
                || ex instanceof BindException) {
            LOG.warn("warning: code={},message={}", code, message);
        } else {
            LOG.error("Exception:", ex);
        }
        ResponseResult<Object> responseResult = new ResponseResult<>(null);
        return responseResult.fail(code, message);
    }
}
