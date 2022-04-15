package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ArtistVo {
    private String id;

    private  String name;

    private ArtistState artistState;

    private String remark;

    private String photoId;

    private FileVo photo;

    private List<MusicVo> musicVoList;

    private List<AlbumVo> albumVoList;


    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    private Integer recommendFactor;

    private Date createdTime;
    private Date updatedTime;
}
