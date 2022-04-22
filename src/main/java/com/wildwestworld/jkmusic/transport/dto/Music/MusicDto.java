package com.wildwestworld.jkmusic.transport.dto.Music;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Album;
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
public class MusicDto {
    private String id;

    private  String name;

    private MusicState musicState;

    private String description;

    private String fileId;

    private FileDto file;

    private String photoId;

    private FileDto photo;

    private List<AlbumDto> albumDtoList;


    private List<ArtistDto> artistDtoList;

    private List<TagDto> tagDtoList;


    private Date createdTime;

    private Date updatedTime;
}
