package com.wildwestworld.jkmusic.transport.dto.Role;


import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserSimpleDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleDto {
    private String  id;

    private String name;

    private String title;

    private List<UserDto> userList;


    private Date createdTime;

    private Date updatedTime;
}
