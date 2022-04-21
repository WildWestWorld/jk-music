package com.wildwestworld.jkmusic.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("tag")
@Data
public class Tag {

    //调用我们写好的自定义ID
    //自定义ID来自utlis文件夹下的ksuid
    @TableId(type = IdType.ASSIGN_ID)
    //我们用id是ksuid 需要在pom导入依赖
    private  String id;


    private String name;


    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;
}
