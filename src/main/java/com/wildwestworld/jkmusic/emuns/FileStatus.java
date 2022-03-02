package com.wildwestworld.jkmusic.emuns;

//上传文件状态 用于FILE

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FileStatus {
    UPLOADING(0,"上传中"),
    UPLOADED(1,"已上传"),
    CANCEL(2,"已取消");

    //给数据库的数据
    @EnumValue
    private Integer key;


    //给前端的数据
    @JsonValue
    private String display;

    FileStatus(Integer key, String display) {
        this.key = key;
        this.display = display;
    }

    public Integer getKey() {
        return key;
    }


    public String getDisplay() {
        return display;
    }


}
