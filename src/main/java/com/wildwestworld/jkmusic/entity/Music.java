package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wildwestworld.jkmusic.emuns.MusicState;
import lombok.Data;
import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.util.List;

@Data
@TableName("music")
public class Music extends AbstractEntity{

    private  String name;

    //@TableField("music_state") 是你数据库中表的属性名，表上是什么就写什么不要改变
    @TableField("music_state")
    private MusicState musicState;

    private String description;

    @TableField(value = "photo_id",updateStrategy = FieldStrategy.IGNORED)


    private String photoId;

    @TableField(exist = false)
    private File photo;

    @TableField("file_id")
    private String fileId;

    @TableField(exist = false)
    private File file;

    @TableField(exist = false)
    private List<Artist> artistList;
}
