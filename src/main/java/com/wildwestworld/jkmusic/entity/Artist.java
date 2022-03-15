package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import lombok.Data;

import java.util.List;

@Data
public class Artist extends AbstractEntity{
    private String name;

    private String remark;

    @TableField("photo_id")
    private String photoId;
    @TableField("artist_state")
    private ArtistState artistState;

    @TableField(exist = false)
    private File photo;

    @TableField(exist = false)
    private List<Music> musicList;


}
