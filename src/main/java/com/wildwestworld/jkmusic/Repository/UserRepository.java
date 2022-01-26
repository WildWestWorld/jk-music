package com.wildwestworld.jkmusic.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wildwestworld.jkmusic.Entity.User;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

//只写需要sql语句的方法
@Mapper
public interface UserRepository extends BaseMapper<User> {

}
