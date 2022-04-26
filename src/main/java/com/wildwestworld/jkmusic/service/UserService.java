package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.Token.TokenCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


//专门写方法名字的地方
//extends UserDetailsService是为了可以重写loadUserByUsername
//loadUserByUsername可以利用username从数据库调取相关的信息
public interface UserService extends UserDetailsService  {

    List<UserDto> getUserList(String searchWord);


    UserDto createUser(UserCreateByRequest userCreateByRequest);

    UserDto updateUserByID(String id, UserUpdateRequest userUpdateRequest);

    void  deleteUserByID(String id);

    IPage<UserDto> getPage(Integer pageNum, Integer pageSize, String searchWord);


    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDto getUserByID(String id);




    String createToken(TokenCreateRequest tokenCreateRequest);

    UserDto getCurrentUser();
}
