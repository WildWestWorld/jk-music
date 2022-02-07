package com.wildwestworld.jkmusic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.mapper.UserMapper;
import com.wildwestworld.jkmusic.repository.UserRepository;
import com.wildwestworld.jkmusic.service.UserService;

import com.wildwestworld.jkmusic.transport.dto.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.dto.UserUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
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
    @Resource
    UserMapper userMapper;

    @GetMapping
   public List<UserVo> list(){

        List<UserDto> userDtoList = userService.list();
        List<UserVo> userVoList = userDtoList.stream().map(userRepository::toVo).collect(Collectors.toList());
        return userVoList;
   }


    //   @RequestBody的作用其实是将json格式的数据转为java对象 ,将前端传过来的对象映射到RequestBody注解过的对象中
    //前端利用DTO媒介到后端，后端返回VO给前端

    // @NotBlank 来自于spring-boot-starter-validation
//需要使用@Validated 配套
// @Validated放在使用该类的Controller层的方法上
   @PostMapping
    public UserVo create(@Validated @RequestBody UserCreateByRequest userCreateByRequest){
        return userRepository.toVo(userService.create(userCreateByRequest));
   }

   @GetMapping("/{id}")
    UserVo getUserByID(@PathVariable String id){
        return userRepository.toVo(userService.getUserByID(id));
   }

   @PutMapping("/{id}")
    UserVo updateUserByID(@PathVariable String id,@Validated @RequestBody UserUpdateRequest userUpdateRequest){
        return userRepository.toVo(userService.updateUserByID(id,userUpdateRequest));
   }
   //Result文件在utils里面 就是个简单的返回值，无聊可以看看
   @DeleteMapping("/{id}")
   Result<?> deleteUserByID(@PathVariable String id){
        userService.deleteUserByID(id);
       return Result.success();
   }

   @GetMapping("/pages")
    Page<UserVo>  getPageByUsername (@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "")String searchWord) {
       Page<UserDto> userDtoPage = userService.getPage(pageNum, pageSize, searchWord);

       List<UserDto> userDtoList = userDtoPage.getRecords();
       List<UserVo> userVoList = userDtoList.stream().map(userRepository::toVo).collect(Collectors.toList());

       Page<UserVo> userVoPage =new Page<>();
       userVoPage.setRecords(userVoList);
       userVoPage.setCurrent(userDtoPage.getCurrent());
       userVoPage.setTotal(userVoList.size());
       userVoPage.setSize(userDtoPage.getSize());
       userVoPage.setSearchCount(userDtoPage.searchCount());

       return userVoPage;
   }

   //获取当前用户
   @GetMapping("/currentUser")
    UserVo getCurrentUser(){
       UserVo userVo = userRepository.toVo(userService.getCurrentUser());

       return userVo;
   }

}

