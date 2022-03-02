package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.emuns.FileType;
import com.wildwestworld.jkmusic.emuns.Storage;
import lombok.Data;

@Data
@TableName("file")
public class File extends AbstractEntity{
    private String name;
    @TableField("hash_key")
    private String hashKey;

    //文件的大小
    private Integer size;

    //后缀名
    private String ext;

    //文件的类型
    @TableField("file_type")
    private FileType fileType;

    //文件的上传状态
    @TableField("file_status")
    private FileStatus fileStatus = FileStatus.UPLOADING;

    //文件存放的仓库
    private Storage storage;
}
