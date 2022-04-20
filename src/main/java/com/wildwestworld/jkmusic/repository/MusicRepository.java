package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.mapper.FileMapper;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

//专门用于Music实体类的dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring",uses = {FileRepository.class})



public interface MusicRepository {
    //将Music类转化为MusicDto
    @Mapping(target="artistDtoList",source="artistList")
    @Mapping(target="albumDtoList",source="albumList")

    MusicDto musicToDto(Music music);


    //将dto转化为Vo
    @Mapping(target="artistVoList",source="artistDtoList")
    @Mapping(target="albumVoList",source="albumDtoList")

    MusicVo musicToVo(MusicDto musicDto);

    //将Dto转换为User实体类Entity
    @Mapping(target="artistList",source="artistIdList")
    Music createMusicEntity (MusicCreateRequest musicCreateRequest);

    @Mapping(target="artistList",source="artistIdList")
    Music UpdateMusicEntity (MusicUpdateRequest musicUpdateRequest);

    default List<Artist> mapArtistList(List<String> artistIdList){
        List<Artist> artistList = new ArrayList();
        for (String id : artistIdList) {
            Artist artist = new Artist();
            artist.setId(id);
            artistList.add(artist);
        }
        return  artistList;
    }

}
