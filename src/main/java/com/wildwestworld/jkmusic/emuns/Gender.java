package com.wildwestworld.jkmusic.emuns;

//用于entity里面user 使用

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    FEMALE(0,"女"),
    MALE(1,"男"),
    UNKNOWN(2,"未知");
    //给数据库的数据
    @EnumValue
    private Integer key;


    //给前端的数据
    @JsonValue
    private String display;

    Gender(Integer key, String display) {
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
