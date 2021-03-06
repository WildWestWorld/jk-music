package com.wildwestworld.jkmusic.exception;


import com.wildwestworld.jkmusic.entity.Music;

//枚举类型，用于标记错误类型
public enum BizExceptionType {
        BAD_REQUEST(400,"请求错误"),
        UNAUTHORIZED(401,"未登录"),
        FORBIDDEN(403,"无权操作"),
        NOT_FOUND(404,"未找到"),
        INNER_ERROR(500,"系统内部错误"),

        User_Name_SAME(40001001,"用户名重复"),
        User_PASSWORD_NOT_MATCH(40001002,"账号密码不匹配"),

        User_NOT_FOUND(40401001,"用户未找到"),

        User_NOT_ENABLED(50001001,"该用户处于未启用状态"),
        User_LOCKED(50001002,"该用户处于封禁状态"),

        Music_NOT_FOUND(40402001,"音乐未找到"),
        File_NOT_FOUND(40403001,"文件未找到"),
        Artist_NOT_FOUND(40404001,"歌手未找到"),
        PlayList_NOT_FOUND(40405001,"歌单未找到"),
        Album_NOT_FOUND(40406001,"专辑未找到"),
        Tag_NOT_FOUND(40407001,"标签未找到"),
        Role_NOT_FOUND(40408001,"角色未找到");


    private Integer code;

        private String message;

     BizExceptionType(Integer code, String message) {
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
