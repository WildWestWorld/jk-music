package com.wildwestworld.jkmusic.handleException;


import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.ErrorResponse;
import com.wildwestworld.jkmusic.exception.ExceptionType;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

//Spring 中 @RestControllerAdvice 注解可以拦截并获取带有 @Controller 或 @RestController 注解类的异常，
// 通过 @ExceptionHandler 注解设置捕获异常类型。

//对于错误的全局控制
@RestControllerAdvice
public class GlobalExceptionHandler {


    // 通过 @ExceptionHandler 注解设置捕获异常类型。

    //捕获自定义异常BizException
    @ExceptionHandler(value = BizException.class)
    public ErrorResponse bizExceptionHandler(BizException e) {
        //把错误信息放到ErrorResponse里面
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTrace(e.getStackTrace());
        return errorResponse;
    }

//    //捕获一般异常
//    @ExceptionHandler(value = Exception.class)
//    public ErrorResponse exceptionHandler(Exception e) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setCode(ExceptionType.INNER_ERROR.getCode());
//        errorResponse.setMessage(ExceptionType.INNER_ERROR.getMessage());
//        return errorResponse;
//    }
//
//
//
//    //捕获无权访问异常
//    @ExceptionHandler(value = AccessDeniedException.class)
//    //@ResponseStatus注解有两种用法，一种是加载自定义异常类上，一种是加在目标方法中
//    //那我们首先类说一下加在目标方法上的这种情况，注解中有两个参数，value属性设置异常的状态码，reaseon是异常的描述，
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponse accessDeniedHandler(Exception e) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setCode(ExceptionType.FORBIDDEN.getCode());
//        errorResponse.setMessage(ExceptionType.FORBIDDEN.getMessage());
//        return errorResponse;
//    }

    //重定义 spring验证无效报错
    //一定要引入import org.springframework.web.bind.MethodArgumentNotValidException;
    //不要引入错了，不然那没法用
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> bizExceptionHandler(MethodArgumentNotValidException e) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(ExceptionType.BAD_REQUEST.getCode());
            errorResponse.setMessage(error.getDefaultMessage());
            errorResponses.add(errorResponse);
            System.out.println(errorResponses);
        });
        return errorResponses;
    }

}
