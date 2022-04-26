package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.wildwestworld.jkmusic.emuns.Gender;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@TableName("user")
@Data

//与AbstractEntity联结  AbstractEntity放的是ID什么的，这样就达到复用的目的
//implements UserDetails 是为了service层的loadUserByUsername（最后还是我们自己用了mybatisplus来写了这个方法）
// 还有是为了 getAuthorities   将role传入到里面，我们就能根据传入的role和@RolesAllowed('')这个注解方法，来控制用户是否能访问该页面了

public class User extends AbstractEntity implements UserDetails {

    private  String username;

    private  String nickname;

    private  String password;

    //数据来源enums里面的gender 是个枚举类型包含我们需要的数据类型
    @TableField("gender")
    private Gender gender;

    private  Boolean locked;
    //这里放个Getter(value = AccessLevel.NONE)，是因为下面overwrite里面有一个了，所以和lombook说不要了
    @Getter(value = AccessLevel.NONE)
    private  Boolean enabled;

    private  String lastLoginIp;

    private Date lastLoginTime;



    //ManyToMany 多对多查询
    //FetchType.LAZY：懒加载，加载一个实体时，定义懒加载的属性不会马上从数据库中加载
    //FetchType.EAGER：急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载

    //1.一对一和多对一的@JoinColumn注解的都是在“主控方”，都是本表指向外表的外键名称。
    //2.一对多的@JoinColumn注解在“被控方”，即一的一方，指的是外表中指向本表的外键名称。

    //3.多对多中，joinColumns写的都是本表在中间表的外键名称，
    //  inverseJoinColumns写的是另一个表在中间表的外键名称。


    // @JoinTable(name = "user_role", user_role 中间表的表名

    //name：当前表的字段 因为  @JoinTable(name = "user_role" 所以当前表是user_role
    //referencedColumnName：引用表对应的字段，如果不注明，默认就是引用表的主键



    @TableField(exist = false)
    private List<Role> roleList;

    @TableField(exist = false)
    private List<PlayList> playList;

    //以下方法皆因UserDetails
    //后期会和role进行绑定
    //getAuthorities
    // 将role传入到里面，我们就能根据传入的role和在Controller层设置@RolesAllowed({'your_role_name'})这个注解方法，来控制用户是否能访问该页面了
    //注意：要使用这个方法之前要在SecurityConfig 要在顶部注解@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //函数的返回值 List 必须是GrantedAuthority属性的所以 我们要自己新建一个GrantedAuthority属性的list
        List<GrantedAuthority> authorities = new ArrayList<>();
        //遍历User里面的roles
        for (Role role : roleList) {
            //既然返回值需要GrantedAuthority所以我们就采用专门存储角色的SimpleGrantedAuthority
            //SimpleGrantedAuthority存储的是role的名字
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
            System.out.println(role);
            System.out.println(simpleGrantedAuthority);
            //需要返回的数组中放入User的Roles
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
//      现在我们可以在Controller层设置  @RolesAllowed({'your_role_name'})这个注解方法，来控制用户是否能访问该页面了
        return  authorities;
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
