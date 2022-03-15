package com.wildwestworld.jkmusic.transport.vo;

import com.wildwestworld.jkmusic.emuns.PlayListState;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlayListVo extends BaseVo  {
    private String id;

    private String name;

    private String description;


    private String coverId;

    private String creatorId;

    private FileDto cover;

    private PlayListState status;

    private UserDto creator;

    private List<MusicDto> musicList;

    private Date createdTime;

    private Date updatedTime;

}