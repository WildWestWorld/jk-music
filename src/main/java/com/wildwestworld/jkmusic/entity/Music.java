package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.MusicState;
import lombok.Data;

@Data
public class Music extends AbstractEntity{
    private  String name;

    //@TableField("music_state") 是你数据库中表的属性名，表上是什么就写什么不要改变
    @TableField("music_state")
    private MusicState musicState;

    private String description;

}
