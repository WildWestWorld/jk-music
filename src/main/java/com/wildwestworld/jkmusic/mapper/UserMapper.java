package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.User;

import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//只写需要sql语句的方法

@Mapper
public interface UserMapper extends BaseMapper<User> {


}
