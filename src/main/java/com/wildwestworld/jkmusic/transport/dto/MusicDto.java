package com.wildwestworld.jkmusic.transport.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.MusicState;
import lombok.Data;

import java.util.Date;

@Data
public class MusicDto {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;

    private String fileId;

    private FileDto file;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
