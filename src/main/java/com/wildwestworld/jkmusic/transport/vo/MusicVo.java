package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.MusicState;
import lombok.Data;

import java.util.Date;
@Data
public class MusicVo {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;

//      注解@JsonFormat主要是后台到前台的时间格式的转换
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
