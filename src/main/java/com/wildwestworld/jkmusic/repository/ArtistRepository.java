package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.mapper.FileMapper;
import com.wildwestworld.jkmusic.mapper.MusicMapper;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = FileRepository.class)
@DecoratedWith(ArtistRepositoryDecorator.class)
public interface ArtistRepository {
    //将Music类转化为MusicDto
    ArtistDto artistToDto(Artist artist);

    //将dto转化为Vo
    ArtistVo artistToVo(ArtistDto artistDto);

    //将Dto转换为User实体类Entity
    Artist createArtistEntity (ArtistCreateRequest artistCreateRequest);
}
