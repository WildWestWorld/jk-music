package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract  class  BaseVo {
    private String id;

    private Date createdTime;

    private Date updatedTime;
}
