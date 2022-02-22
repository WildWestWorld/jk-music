package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.mapstruct.Mapper;


//专门用于dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring")
public interface UserRepository  {
    //将user类转化为UserDto
    UserDto toDto(User user);

    //将dto转化为Vo
    UserVo toVo(UserDto userDto);

    //将Dto转换为User实体类Entity
    User createEntity (UserCreateByRequest userCreateByRequest);
}