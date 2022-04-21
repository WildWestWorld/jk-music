package com.wildwestworld.jkmusic.transport.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TagVo {
    private  String id;

    private String name;

    private Date createdTime;

    private Date updatedTime;
}
