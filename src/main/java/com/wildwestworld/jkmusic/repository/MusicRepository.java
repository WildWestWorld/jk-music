package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.mapstruct.Mapper;

//专门用于Music实体类的dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring")
public interface MusicRepository {
    //将Music类转化为MusicDto
    MusicDto musicToDto(Music music);

    //将dto转化为Vo
    MusicVo musicToVo(MusicDto musicDto);

    //将Dto转换为User实体类Entity
    Music createMusicEntity (MusicCreateRequest musicCreateRequest);
}
