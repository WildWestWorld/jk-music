package com.wildwestworld.jkmusic.transport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.emuns.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class UserVo {
    private String id;

    private String username;

    private String nickname;

    private Gender gender;

    private Boolean locked;

    private Boolean enabled;

    @TableField(exist = false)
    private List<RoleVo> roles;
}
