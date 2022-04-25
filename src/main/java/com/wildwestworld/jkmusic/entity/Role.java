package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("role")
@Data
public class Role extends AbstractEntity{


    private String name;
    private String title;

    @TableField(exist = false)
    private List<User> userList;

}
