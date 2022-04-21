package com.wildwestworld.jkmusic.transport.dto.Tag;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TagDto {

    private  String id;

    private String name;

    private Date createdTime;

    private Date updatedTime;
}
