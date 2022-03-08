package com.wildwestworld.jkmusic.transport.dto;

import com.wildwestworld.jkmusic.emuns.PlayListState;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlayListDto {

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
