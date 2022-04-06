package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wildwestworld.jkmusic.emuns.PlayListState;
import lombok.Data;

import java.util.List;

@Data
@TableName("playlist")
public class PlayList extends AbstractEntity{
    private String name;

    private String description;

    @TableField("cover_id")
    private String coverId;

    @TableField("creator_id")
    private String creatorId;

    @TableField("playlist_state")
    private PlayListState playListState;

    @TableField(exist = false)
    private File cover;

    @TableField(exist = false)
    private User creator;

    @TableField(exist = false)
    private List<Music> musicList;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    @TableField("recommend_factor")
    private Integer recommendFactor;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean special;
}
