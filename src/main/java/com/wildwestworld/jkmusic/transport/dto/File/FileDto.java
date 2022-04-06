package com.wildwestworld.jkmusic.transport.dto.File;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.emuns.FileType;
import com.wildwestworld.jkmusic.emuns.Storage;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FileDto {
    private String id;

    private String name;

    private String hashKey;

    //文件的大小
    private Integer size;

    //后缀名
    private String ext;

    //文件的类型
    private FileType fileType;

    //文件的状态
    private FileStatus fileStatus;

    //文件存放的仓库
    private Storage storage;

    private String url;


    private Date createdTime;



    private Date updatedTime;
}
