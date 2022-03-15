package com.wildwestworld.jkmusic.transport.dto.File;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.emuns.FileType;
import com.wildwestworld.jkmusic.emuns.Storage;
import lombok.Data;

import java.util.Date;

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


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;



    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;
}
