package com.wildwestworld.jkmusic.transport.dto.Artist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArtistDto {
    private String id;

    private  String name;

    private ArtistState artistState;

    private String remark;

    private String photoId;

    private FileDto photo;

    private List<MusicDto> musicDtoList;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    private Integer recommendFactor;

    private Date createTime;

    private Date updateTime;
}
