package com.wildwestworld.jkmusic.repository;


import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.vo.AlbumVo;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {FileRepository.class,MusicRepository.class})


public interface AlbumRepository {
    //将Music类转化为MusicDto
    @Mapping(target="musicDtoList",source="musicList")


    AlbumDto albumToDto(Album album);



    //将dto转化为Vo
    @Mapping(target="musicVoList",source="musicDtoList")

    AlbumVo albumToVo(AlbumDto albumDto);

    //将Dto转换为User实体类Entity
    Album createAlbumEntity (AlbumCreateRequest AlbumCreateRequest);
}
