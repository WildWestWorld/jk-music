package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.AlbumState;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import lombok.Data;

import java.util.List;

@Data
public class Album extends AbstractEntity{
    private String name;

    private String description;

    @TableField("photo_id")
    private String photoId;
    @TableField("album_state")
    private AlbumState albumState;

    @TableField(exist = false)
    private File photo;

    @TableField(exist = false)
    private List<Music> musicList;


    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    @TableField("recommend_factor")
    private Integer recommendFactor;


}
