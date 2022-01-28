package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.UserCreateDto;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


//专门写方法名字的地方
//extends UserDetailsService是为了可以重写loadUserByUsername
//loadUserByUsername可以利用username从数据库调取相关的信息
public interface UserService extends UserDetailsService {

    List<UserDto> list();


    UserDto create(UserCreateDto userCreateDto);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
