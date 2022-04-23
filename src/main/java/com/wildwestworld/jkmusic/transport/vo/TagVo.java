package com.wildwestworld.jkmusic.transport.vo;

import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TagVo {
    private  String id;

    private String name;


    private List<MusicVo> musicList;

    private List<PlayListVo> playList;


    private Date createdTime;

    private Date updatedTime;
}
