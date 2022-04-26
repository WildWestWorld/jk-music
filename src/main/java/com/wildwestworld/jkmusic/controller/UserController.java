package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.mapper.UserMapper;
import com.wildwestworld.jkmusic.repository.UserRepository;
import com.wildwestworld.jkmusic.service.UserService;

import com.wildwestworld.jkmusic.transport.dto.User.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserUpdateRequest;
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


    @GetMapping
   public List<UserVo> getUserList(@RequestParam(defaultValue = "")String searchWord){

        List<UserDto> userDtoList = userService.getUserList(searchWord);
        List<UserVo> userVoList = userDtoList.stream().map(userRepository::toVo).collect(Collectors.toList());
        return userVoList;
   }


    //   @RequestBody的作用其实是将json格式的数据转为java对象 ,将前端传过来的对象映射到RequestBody注解过的对象中
    //前端利用DTO媒介到后端，后端返回VO给前端

    // @NotBlank 来自于spring-boot-starter-validation
//需要使用@Validated 配套
// @Validated放在使用该类的Controller层的方法上
   @PostMapping
    public UserVo createUser(@Validated @RequestBody UserCreateByRequest userCreateByRequest){
        return userRepository.toVo(userService.createUser(userCreateByRequest));
   }

   @GetMapping("/{id}")
    UserVo getUserByID(@PathVariable String id){
        return userRepository.toVo(userService.getUserByID(id));
   }

   @PutMapping("/{id}")
    UserVo updateUserByID(@PathVariable String id,@Validated @RequestBody UserUpdateRequest userUpdateRequest){
       UserDto userDto = userService.updateUserByID(id, userUpdateRequest);
       UserVo userVo = userRepository.toVo(userDto);
       return userVo;
   }
   //Result文件在utils里面 就是个简单的返回值，无聊可以看看
   @DeleteMapping("/{id}")
   Result<?> deleteUserByID(@PathVariable String id){
        userService.deleteUserByID(id);
       return Result.success();
   }

   @GetMapping("/pages")
   IPage<UserVo> getPageByUsername (@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "")String searchWord) {
       IPage<UserDto> userDtoPage = userService.getPage(pageNum, pageSize, searchWord);

       List<UserDto> userDtoList = userDtoPage.getRecords();
       List<UserVo> userVoList = userDtoList.stream().map(userRepository::toVo).collect(Collectors.toList());

       IPage<UserVo> userVoPage =new Page<>(pageNum,pageSize);
       userVoPage.setRecords(userVoList);
       userVoPage.setCurrent(userDtoPage.getCurrent());
       userVoPage.setTotal(userDtoPage.getTotal());
       userVoPage.setSize(userDtoPage.getSize());


       return userVoPage;
   }

   //获取当前用户
   @GetMapping("/currentUser")
    UserVo getCurrentUser(){
       UserVo userVo = userRepository.toVo(userService.getCurrentUser());

       return userVo;
   }

}

