package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MusicVo {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;



    private FileVo file;

    private List<ArtistVo> artistVoList;


    //      注解@JsonFormat主要是后台到前台的时间格式的转换
    private Date createTime;
    private Date updateTime;
}
