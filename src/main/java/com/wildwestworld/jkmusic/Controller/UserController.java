package com.wildwestworld.jkmusic.Controller;

import com.wildwestworld.jkmusic.Mapper.UserMapper;
import com.wildwestworld.jkmusic.Repository.UserRepository;
import com.wildwestworld.jkmusic.Service.UserService;

import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;


    @GetMapping
   public List<UserVo> list(){

        List<UserDto> userDtoList = userService.list();
        List<UserVo> userVoList = userDtoList.stream().map(userMapper::toVo).collect(Collectors.toList());
        return userVoList;
   }





}
