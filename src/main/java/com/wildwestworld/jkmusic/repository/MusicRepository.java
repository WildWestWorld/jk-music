package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.mapper.FileMapper;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

//专门用于Music实体类的dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring",uses = {FileRepository.class})



public interface MusicRepository {
    //将Music类转化为MusicDto
    MusicDto musicToDto(Music music);


    //将dto转化为Vo
    MusicVo musicToVo(MusicDto musicDto);

    //将Dto转换为User实体类Entity
    Music createMusicEntity (MusicCreateRequest musicCreateRequest);
}
