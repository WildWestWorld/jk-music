package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wildwestworld.jkmusic.emuns.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@TableName("user")
@Data
//与AbstractEntity联结  AbstractEntity放的是ID什么的，这样就达到复用的目的
//implements UserDetails 是为了service层的loadUserByUsername
public class User extends AbstractEntity implements UserDetails {



    private  String username;

    private  String nickname;

    private  String password;

    //数据来源enums里面的gender 是个枚举类型包含我们需要的数据类型
    @TableField("gender")
    private Gender gender;

    private  Boolean locked;
    @Getter(value = AccessLevel.NONE)
    private  Boolean enabled;

    private  String lastLoginIp;

    private Date lastLoginTime;

    @TableField(exist = false)
    private List<Role> roles;



    //后期会和role进行绑定
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    //当前用户是否  没有过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    //当前用户是否  没有锁定
    @Override
    public boolean isAccountNonLocked() {
        return !getLocked();
    }

    //密码验证是否 没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可以用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
