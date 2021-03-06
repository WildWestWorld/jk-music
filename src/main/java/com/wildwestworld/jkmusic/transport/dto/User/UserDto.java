package com.wildwestworld.jkmusic.transport.dto.User;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.Gender;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String id;

    private String username;

    private String nickname;

    private List<RoleDto> roleList;

    private List<PlayListDto> playList;


    private Gender gender;

    private  Boolean locked;

    private  Boolean enabled;

    private  String lastLoginIp;

    private Date lastLoginTime;
}
