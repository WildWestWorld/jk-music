package com.wildwestworld.jkmusic.transport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.emuns.FileType;
import com.wildwestworld.jkmusic.emuns.Storage;
import lombok.Data;

import java.util.Date;

@Data
public class FileVo  extends BaseVo{

    private String name;

    private String hashKey;

    //文件的大小
    private Integer size;

    //后缀名
    private String ext;

    //文件的类型
    private FileType fileType;

    //文件的上传状态
    private FileStatus fileStatus;

    //文件存放的仓库
    private Storage storage;



}
