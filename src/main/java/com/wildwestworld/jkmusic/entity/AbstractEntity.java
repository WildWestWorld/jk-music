package com.wildwestworld.jkmusic.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

//用于放一些记忆类的属性，比如id,updateTime
//可以给各种需要ID和updateTime属性的实体类使用
@Data
public abstract class AbstractEntity {
    //调用我们写好的自定义ID
    //自定义ID来自utlis/ksuid
    @TableId(type = IdType.ASSIGN_UUID)

    //我们用id是ksuid 需要在pom导入依赖
    private  String id;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;
}
