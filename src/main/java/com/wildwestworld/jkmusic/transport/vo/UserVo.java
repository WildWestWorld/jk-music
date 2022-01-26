package com.wildwestworld.jkmusic.transport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;
@Data
public class UserVo {
    private String id;

    private String username;

    private String nickname;

    @TableField(exist = false)
    private List<RoleVo> roles;
}
