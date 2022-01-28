package com.wildwestworld.jkmusic.exception;


//枚举类型，用于标记错误类型
public enum ExceptionType {
        BAD_REQUEST(400,"请求错误"),
        UNAUTHORIZED(401,"未登录"),
        FORBIDDEN(403,"无权操作"),
        NOT_FOUND(404,"未找到"),
        INNER_ERROR(500,"系统内部错误"),
        User_Name_SAME(40001001,"用户名重复"),
        User_NOT_FOUND(40401001,"用户未找到");

        private Integer code;

        private String message;

     ExceptionType(Integer code, String message) {
        this.code=code;
         this.message=message;
        }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
