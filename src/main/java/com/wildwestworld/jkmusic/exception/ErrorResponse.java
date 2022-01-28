package com.wildwestworld.jkmusic.exception;


import lombok.Data;

@Data
//对于错误的响应，用于handleException的GlobalException里面
public class ErrorResponse {
    private Integer code;
    private String message;
    private Object trace;

}
