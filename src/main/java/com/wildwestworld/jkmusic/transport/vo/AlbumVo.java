package com.wildwestworld.jkmusic.transport.vo;

import com.wildwestworld.jkmusic.emuns.AlbumState;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.entity.Artist;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AlbumVo {
    private String id;

    private  String name;

    private AlbumState albumState;

    private String description;

    private String photoId;

    private FileVo photo;

    private List<MusicVo> musicVoList;

    private List<ArtistVo> albumArtistList;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    private Integer recommendFactor;

    private Date createdTime;
    private Date updatedTime;
}
