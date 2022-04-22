package com.wildwestworld.jkmusic.transport.dto.Tag;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TagDto {

    private  String id;

    private String name;

    private Date createdTime;

    private Date updatedTime;

    private List<MusicDto> musicList;


}
