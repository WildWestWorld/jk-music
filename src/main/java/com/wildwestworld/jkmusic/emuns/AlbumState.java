package com.wildwestworld.jkmusic.emuns;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AlbumState {
    待上架(0,"待上架"),
    已上架(1,"已上架"),
    已下架(2,"已下架");



    //给数据库的数据
    @EnumValue
    private Integer key;


    //给前端的数据
    @JsonValue
    private String display;

    AlbumState(Integer key, String display) {
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
