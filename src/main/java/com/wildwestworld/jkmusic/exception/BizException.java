package com.wildwestworld.jkmusic.exception;
//用于处理异常，继承自RuntimeException异常，也就意味着这是我们自定义的异常
public class BizException extends RuntimeException{
    private final Integer code;

    public BizException(BizExceptionType bizExceptionType){
        //super使用继承类里面的函数
        //这个函数就是打印出一个信息
        super(bizExceptionType.getMessage());
        this.code = bizExceptionType.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
