package com.wildwestworld.jkmusic.transport.dto.PlayList;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class PlayListUpdateRequest {
    @NotBlank(message = "更新的歌单名不能为空")
    private String name;

    private String description;

    private String playListState;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended;

    //    推荐因子
    private Integer recommendFactor;

    //    根据布尔值的真与假判断是否为特色歌单
    private Boolean special;

    @NotNull(message = "更新的封面不能为空")
    private String coverId;
}
