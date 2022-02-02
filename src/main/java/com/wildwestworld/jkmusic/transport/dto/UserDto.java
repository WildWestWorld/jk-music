package com.wildwestworld.jkmusic.transport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.Gender;
import com.wildwestworld.jkmusic.transport.vo.RoleVo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String id;

    private String username;

    private String nickname;

    @TableField(exist = false)
    private List<RoleDto> roles;

    private Gender gender;

    private  Boolean locked;

    private  Boolean enabled;

    private  String lastLoginIp;

    private Date lastLoginTime;
}
