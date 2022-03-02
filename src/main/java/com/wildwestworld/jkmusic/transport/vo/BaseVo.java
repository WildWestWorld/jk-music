package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract  class  BaseVo {
    private String id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date createdTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date updatedTime;
}
