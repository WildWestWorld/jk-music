package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ArtistVo {
    private String id;

    private  String name;

    private ArtistState artistState;

    private String remark;

    private String photoId;

    private FileVo photo;

    private List<MusicVo> musicVoList;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
