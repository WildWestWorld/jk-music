package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wildwestworld.jkmusic.entity.User;

import org.apache.ibatis.annotations.Mapper;

//只写需要sql语句的方法

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
