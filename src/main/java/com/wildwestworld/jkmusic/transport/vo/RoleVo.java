package com.wildwestworld.jkmusic.transport.vo;

import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

//VO专门用于接口的返回值 也就是后端传给前端的数据
@Data
public class RoleVo {
    private String  id;

    private String name;

    private String title;

    private List<UserVo> userList;


    private Date createdTime;

    private Date updatedTime;
}
