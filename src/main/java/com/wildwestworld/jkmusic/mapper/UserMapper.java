package com.wildwestworld.jkmusic.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//只写需要sql语句的方法

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> userList ();
    IPage<User> getPage(Page<User> page , @Param("username") String username);
    User getCurrentUser (@Param("username") String username);
}
