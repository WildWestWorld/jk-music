package com.wildwestworld.jkmusic.controller;

import com.wildwestworld.jkmusic.mapper.UserMapper;
import com.wildwestworld.jkmusic.repository.UserRepository;
import com.wildwestworld.jkmusic.service.UserService;

import com.wildwestworld.jkmusic.transport.dto.UserCreateDto;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Resource
    UserService userService;
    @Resource
    UserRepository userRepository;


    @GetMapping
   public List<UserVo> list(){

        List<UserDto> userDtoList = userService.list();
        List<UserVo> userVoList = userDtoList.stream().map(userRepository::toVo).collect(Collectors.toList());
        return userVoList;
   }
   @PostMapping
//   @RequestBody的作用其实是将json格式的数据转为java对象 ,将前端传过来的对象映射到RequestBody注解过的对象中
   //前端利用DTO媒介到后端，后端返回VO给前端
    public UserVo create(@RequestBody UserCreateDto userCreateDto){
        return userRepository.toVo(userService.create(userCreateDto));
   }


}
