package com.wildwestworld.jkmusic.emuns;

//文件类型 用于FILE

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FileType {
    AUDIO(0,"音频"),
    IMAGE(1,"图片"),
    VIDEO(2,"视频"),
    OTHER(3,"其他");
    //给数据库的数据
    @EnumValue
    private Integer key;


    //给前端的数据
    @JsonValue
    private String display;

    FileType(Integer key, String display) {
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
