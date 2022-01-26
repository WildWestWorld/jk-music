package com.wildwestworld.jkmusic.transport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wildwestworld.jkmusic.transport.vo.RoleVo;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;

    private String username;

    private String nickname;

    @TableField(exist = false)
    private List<RoleVo> roles;
}
