package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableField("file_id")
    private String fileId;

    @TableField(exist = false)
    private File file;

    @TableField(exist = false)
    private List<Artist> artistList;
}
