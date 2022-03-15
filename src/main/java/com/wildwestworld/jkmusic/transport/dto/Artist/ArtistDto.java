package com.wildwestworld.jkmusic.transport.dto.Artist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArtistDto {
    private String id;

    private  String name;

    private ArtistState artistState;

    private String remark;

    private String photoId;

    private FileDto photo;

    private List<MusicDto> musicDtoList;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
