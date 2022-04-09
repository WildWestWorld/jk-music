package com.wildwestworld.jkmusic.transport.dto.PlayList;

import com.wildwestworld.jkmusic.emuns.PlayListState;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Data
public class PlayListCreateRequest {

    @NotBlank(message = "歌单名不能为空")
    private String name;

    private String description;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended=false;

    //    推荐因子
    private Integer recommendFactor =0;

    //    根据布尔值的真与假判断是否为特色歌单
    private Boolean special=false;

    @NotNull(message = "请上传封面")
    private String coverId;


}
