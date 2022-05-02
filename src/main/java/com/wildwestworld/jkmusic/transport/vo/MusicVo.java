package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MusicVo {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;


    private FileVo photo;

    private FileVo file;

    private FileVo lyc;


    private List<AlbumVo> albumVoList;

    private List<ArtistVo> artistVoList;


    private List<Tag> tagList;


    //      注解@JsonFormat主要是后台到前台的时间格式的转换
    private Date createdTime;
    private Date updatedTime;
}
