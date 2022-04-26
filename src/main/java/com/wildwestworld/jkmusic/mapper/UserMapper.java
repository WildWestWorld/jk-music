package com.wildwestworld.jkmusic.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Role;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//只写需要sql语句的方法

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectUserById(@Param("id") String id);


    List<User> getUserList (@Param("name") String name);


    IPage<User> getPage(Page<User> page , @Param("username") String username);


    User getCurrentUser (@Param("username") String username);


    int batchInsertUserRole(@Param("user") User entity, @Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteUserRoleById(@Param("user")User entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllUserRoleById(@Param("user")User entity);


    int batchInsertUserPlayList(@Param("user") User entity, @Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteUserPlayListById(@Param("user")User entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllUserPlayListById(@Param("user")User entity);


    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User entity);
}
