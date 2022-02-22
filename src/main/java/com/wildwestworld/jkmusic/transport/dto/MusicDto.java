package com.wildwestworld.jkmusic.transport.dto;


import com.wildwestworld.jkmusic.emuns.MusicState;
import lombok.Data;

import java.util.Date;

@Data
public class MusicDto {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;

    private Date createTime;

    private Date updateTime;
}
