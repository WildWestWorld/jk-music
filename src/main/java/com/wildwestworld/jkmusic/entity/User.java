package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wildwestworld.jkmusic.emuns.Gender;
import lombok.Data;

import java.util.Date;
import java.util.List;

@TableName("user")
@Data
//与AbstractEntity联结
public class User extends AbstractEntity{



    private  String username;

    private  String nickname;

    private  String password;

    //数据来源enums里面的gender 是个枚举类型包含我们需要的数据类型
    @TableField("gender")
    private Gender gender;

    private  Boolean locked;

    private  Boolean enabled;

    private  String lastLoginIp;

    private Date lastLoginTime;

    @TableField(exist = false)
    private List<Role> roles;

}
