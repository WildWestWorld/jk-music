package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.TokenCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.dto.UserUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


//专门写方法名字的地方
//extends UserDetailsService是为了可以重写loadUserByUsername
//loadUserByUsername可以利用username从数据库调取相关的信息
public interface UserService extends UserDetailsService  {

    List<UserDto> list();


    UserDto create(UserCreateByRequest userCreateByRequest);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDto getUserByID(String id);

    UserDto updateUserByID(String id, UserUpdateRequest userUpdateRequest);
    void  deleteUserByID(String id);

    Page<UserDto> getPage( Integer pageNum, Integer pageSize,String searchWord);


    String createToken(TokenCreateRequest tokenCreateRequest);

    UserDto getCurrentUser();
}
