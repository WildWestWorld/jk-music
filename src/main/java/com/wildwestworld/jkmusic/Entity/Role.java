package com.wildwestworld.jkmusic.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("Role")
@Data
public class Role extends AbstractEntity{
    private String name;
    private String title;

}
