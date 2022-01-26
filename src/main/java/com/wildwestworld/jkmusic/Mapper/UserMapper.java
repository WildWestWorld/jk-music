package com.wildwestworld.jkmusic.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wildwestworld.jkmusic.Entity.User;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

//专门用于dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring")
public interface UserMapper  {
    //将user类转化为toDto
    UserDto toDto(User user);

    //将dto转化为Vo
    UserVo toVo(UserDto userDto);

}
