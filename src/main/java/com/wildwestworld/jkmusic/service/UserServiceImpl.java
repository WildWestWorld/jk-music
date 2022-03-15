package com.wildwestworld.jkmusic.service;


import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wildwestworld.jkmusic.config.SecurityConfig;
import com.wildwestworld.jkmusic.emuns.Gender;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.UserMapper;
import com.wildwestworld.jkmusic.repository.UserRepository;
import com.wildwestworld.jkmusic.transport.dto.Token.TokenCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;

import com.wildwestworld.jkmusic.transport.dto.User.UserUpdateRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
//专门实现方法的地方
@Service
public class UserServiceImpl  implements UserService{

    @Resource
    UserMapper userMapper;
    @Resource
    UserRepository userRepository;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> list() {
        List<User> userList = userMapper.userList();
        System.out.println(userList);
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
            throw new BizException(BizExceptionType.User_Name_SAME);
        }
    }

//根据用户名查询用户
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        //新建一个查询器
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//        wrapper.eq(User::getUsername,username);
//        User user = userMapper.selectOne(wrapper);

        User currentUser = userMapper.getCurrentUser(username);
//        if(user == null){
//            //自定义的异常类  自定义的异常类的信息在exception里面
//            throw new BizException(BizExceptionType.User_NOT_FOUND);
//        }
        System.out.println(currentUser);
        return currentUser;
    }
//创造token
    @Override
    public String createToken(TokenCreateRequest tokenCreateRequest) {
        //首先先查询有没有这个用户名
        //loadUserByUsername 自定义方法，就在此页面，有兴趣自己看
        User user = loadUserByUsername(tokenCreateRequest.getUsername());
        //匹配密码是否正确
        //使用passwordEncoder的matches方法来匹配用户输出的密码和User的密码
        //如果不匹配
        if(!passwordEncoder.matches(tokenCreateRequest.getPassword(),user.getPassword())){
            throw new BizException(BizExceptionType.User_PASSWORD_NOT_MATCH);
        }
        //如果用户是未启用的状态
        if (!user.isEnabled()){
            throw new BizException(BizExceptionType.User_NOT_ENABLED);
        }
        //如果用户不是 未锁定的状态 ，也就是用户处于锁定状态
        if (!user.isAccountNonLocked()){
            throw new BizException(BizExceptionType.User_LOCKED);
        }


        //创建token
        //创建JWT的构造器
        JWTCreator.Builder builder = JWT.create();

        //withClaim("userName", saveUsername) 存放信息userName
        String token = builder.withSubject(user.getUsername())
                //System.currentTimeMillis() 获得的是自1970-1-01 00:00:00.000 到当前时刻的时间距离,类型为long
                //SecurityConfig.EXPIRATION_TIME来自于我们自定义的数据
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET));

        return token;
    }

//获得当前用户
    @Override
    public UserDto getCurrentUser() {
        //获得储存在SecurityContextHolder里面的token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //authentication 可以用getName/getPrincipal().toString()
        // 是因为 我们在JWTAuthenticationFilter传入进去的，具体可以看filter里面的JWTAuthenticationFilter
        //然后我们利用获得到的username 和自定义方法loadUserByUsername 获取到user
        User currentUser = loadUserByUsername(authentication.getPrincipal().toString());


        //最后把他转化为dto形式
        UserDto currentUserDto = userRepository.toDto(currentUser);

        return currentUserDto;
    }

    @Override
    public UserDto getUserByID(String id) {
        //根据id查询
        User user = userMapper.selectById(id);
        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.User_NOT_FOUND);
        }
        return userRepository.toDto(user);
    }

    @Override
    public UserDto updateUserByID(String id, UserUpdateRequest userUpdateRequest) {
        User user = userMapper.selectById(id);

        if(user == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.User_NOT_FOUND);
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
            throw new BizException(BizExceptionType.User_NOT_FOUND);
        }

        //根据id删除
        userMapper.deleteById(id);
    }

    @Override
    public IPage<UserDto> getPage(Integer PageNum, Integer pageSize, String searchWord) {


//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//
//        if (StrUtil.isNotBlank(searchWord)){
//            wrapper.like(User::getUsername,searchWord);
//        }

        IPage<User> userPage = userMapper.getPage(new Page<>(PageNum, pageSize), searchWord);
        List<User> userList = userPage.getRecords();

        List<UserDto> UserDtoList = userList.stream().map(userRepository::toDto).collect(Collectors.toList());

        IPage<UserDto> UserDtoPage =new Page<>(PageNum,pageSize);
        UserDtoPage.setRecords(UserDtoList);
        UserDtoPage.setCurrent(userPage.getCurrent());
        UserDtoPage.setTotal(userPage.getTotal());
        UserDtoPage.setSize(userPage.getSize());

        return UserDtoPage;
    }


}
