package com.wildwestworld.jkmusic.transport.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TagVo {
    private  String id;

    private String name;


    private List<MusicVo> musicList;

    private Date createdTime;

    private Date updatedTime;
}
