package com.github.eiriksgata.mockserver.exception;


import com.github.eiriksgata.mockserver.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @author Snake
 * @date 2019/03/13
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * default exception handler(框架异常)
     *
     * @param ex framework exception
     * @return framework error
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseBean<String> defaultErrorHandler(Exception ex) {
        // simple print stack trace
        ex.printStackTrace();
        //log.error(ExceptionUtils.getExceptionAllInfo(ex));
        return ResponseBean.error(ex.getMessage());
    }

    /**
     * validation exception(接口参数校验异常)
     *
     * @param request request
     * @param ex      validation exception
     * @return validation error
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseBean<String> handleMethodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Invalid Request:\n");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append("\n");
        }
        // simple print stack trace
        ex.printStackTrace();
        return ResponseBean.error(errorMessage.toString());
    }

    /**
     * common exception handler(业务异常)
     *
     * @param request request
     * @param ex      business exception
     * @return business error
     */
    @ExceptionHandler(value = CommonBaseException.class)
    @ResponseBody
    public ResponseBean<?> handleCommonException(HttpServletRequest request, CommonBaseException ex) {
        //ex.printStackTrace();
        log.error(ex.getErrMsg());
        return ResponseBean.error(CommonBaseExceptionEnum.getExceptionEnumByCode(ex.getErrCode()));
    }

    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseBody
    public ResponseBean<?> handleNumberFormatException(NumberFormatException ex) {
        ex.printStackTrace();
        return ResponseBean.error(ExceptionUtils.getMessage(ex));
    }


}
