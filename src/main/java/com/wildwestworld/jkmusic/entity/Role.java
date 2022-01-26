package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role")
@Data
public class Role extends AbstractEntity{
    private String name;
    private String title;

}
