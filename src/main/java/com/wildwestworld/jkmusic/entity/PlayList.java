package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.PlayListState;
import lombok.Data;

import java.util.List;

@Data
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
}
