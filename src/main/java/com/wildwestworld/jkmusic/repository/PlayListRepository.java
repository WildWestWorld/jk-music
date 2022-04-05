package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import com.wildwestworld.jkmusic.transport.vo.PlayListVo;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayListRepository {
    //将user类转化为UserDto
    PlayListDto playListToDto(PlayList playList);

    //将dto转化为Vo
    PlayListVo playListToVo(PlayListDto playListDto);

}
