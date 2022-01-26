package com.wildwestworld.jkmusic.Service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.wildwestworld.jkmusic.Entity.User;
import com.wildwestworld.jkmusic.Mapper.UserMapper;
import com.wildwestworld.jkmusic.Repository.UserRepository;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;
    @Resource
    UserRepository userRepository;


    @Override
    public List<UserDto> list() {
        //新建一个查询器
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        //获取所有的username ,从username里面筛选我们想要的结果，我们帅选条件是无，所以每一条数据我们都会获取到
        wrapper.like(User::getUsername,"");
        //使用selectList（我们定义好的查询器），我们就能获取数据库所有的对象了
        List<User> userList = userRepository.selectList(wrapper);
        //这个步骤是把List<User>转化为List<UserDto>
        //stream（）把数据转化为流的形式，然后用map遍历所有的List 执行usermapper里面的方法toDto，让他们变成dto格式的属性
        //然后使用collect()结束掉steam 使用collections.toList 把单个的dto数据变成List
        List<UserDto> userDtoList = userList.stream().map(userMapper::toDto).collect(Collectors.toList());
        return userDtoList;
    }

}
