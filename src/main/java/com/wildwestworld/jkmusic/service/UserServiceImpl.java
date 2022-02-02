package com.wildwestworld.jkmusic.service;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.Gender;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.ExceptionType;
import com.wildwestworld.jkmusic.mapper.UserMapper;
import com.wildwestworld.jkmusic.repository.UserRepository;
import com.wildwestworld.jkmusic.transport.dto.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.UserDto;

import com.wildwestworld.jkmusic.transport.dto.UserUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
//专门实现方法的地方
@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;
    @Resource
    UserRepository userRepository;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> list() {
        //新建一个查询器
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        //获取所有的username ,从username里面筛选我们想要的结果，我们筛选条件是无，所以每一条数据我们都会获取到
        wrapper.like(User::getUsername,"");
        //使用selectList（我们定义好的查询器），我们就能获取数据库所有的对象了
        List<User> userList = userMapper.selectList(wrapper);
        //这个步骤是把List<User>转化为List<UserDto>
        //stream（）把数据转化为流的形式，然后用map遍历所有的List 执行usermapper里面的方法toDto，让他们变成dto格式的属性
        //然后使用collect()结束掉steam 使用collections.toList 把单个的dto数据变成List
        List<UserDto> userDtoList = userList.stream().map(userRepository::toDto).collect(Collectors.toList());
        return userDtoList;
    }


    @Override
    public UserDto create(UserCreateByRequest userCreateByRequest) {
        checkUserName(userCreateByRequest.getUsername());
        User user = userRepository.createEntity(userCreateByRequest);
        //BCryptPasswordEncoder来自于来自于springSecurity
        //使用BCryptPasswordEncoder给用户的密码加密
        //encodePassword就是加密后的密码
        String encodePassword = passwordEncoder.encode(user.getPassword());
        //将密码设置为加密后的密码
        user.setPassword(encodePassword);
        //在数据库中创建
        userMapper.insert(user);
        //创建完了就传出来
         UserDto userDto = userRepository.toDto(user);
         return userDto;
    }
    //查询是否有相同的用户名
    private void checkUserName(String username){
        //新建一个查询器
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        if (user != null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(ExceptionType.User_Name_SAME);
        }
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        //新建一个查询器
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(ExceptionType.User_NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserDto getUserByID(String id) {
        //根据id查询
        User user = userMapper.selectById(id);
        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(ExceptionType.User_NOT_FOUND);
        }
        return userRepository.toDto(user);
    }

    @Override
    public UserDto updateUserByID(String id, UserUpdateRequest userUpdateRequest) {
        User user = userMapper.selectById(id);

        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(ExceptionType.User_NOT_FOUND);
        }

        //如果userUpdateRequest的username不是空的
        if (StrUtil.isNotEmpty(userUpdateRequest.getUsername())){
            //检查是否有相同的Username
            //checkUserName 是自定义的方法，有兴趣自己看
            checkUserName(userUpdateRequest.getUsername());
            //设置名字
            user.setUsername(userUpdateRequest.getUsername());
        }

        //如果userUpdateRequest的Gender不是空的
        if (StrUtil.isNotEmpty(userUpdateRequest.getGender())) {
            //设置性别
            //获取文字形式的gender
            String gender = userUpdateRequest.getGender();
            //利用文字形式的gender和valueOf获取枚举形式的gender
            Gender updateGender = Gender.valueOf(gender);
            user.setGender(updateGender);
        }

        //如果userUpdateRequest的Nickname不是空的
        if (StrUtil.isNotEmpty(userUpdateRequest.getNickname())) {
            //设置昵称
            user.setNickname(userUpdateRequest.getNickname());
        }

        //更新user
        userMapper.updateById(user);
        //再次查询user
        User updateUser = userMapper.selectById(id);
        return userRepository.toDto(updateUser);
    }

    @Override
    public void deleteUserByID(String id) {
        User user = userMapper.selectById(id);

        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(ExceptionType.User_NOT_FOUND);
        }

        //根据id删除
        userMapper.deleteById(id);
    }


}
