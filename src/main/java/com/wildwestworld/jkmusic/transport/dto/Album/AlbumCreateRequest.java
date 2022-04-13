package com.wildwestworld.jkmusic.transport.dto.Album;

import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AlbumCreateRequest {
    @NotBlank(message = "歌手名称不能为空")
    private String name;

    private String description;

    private String albumState;

    //    根据布尔值的真与假判断是否被推荐
    private Boolean recommended=false;

    //    推荐因子
    private Integer recommendFactor =0;

    @NotBlank(message = "封面文件/封面ID不能为空")
    private String photoId;


    private List<String> musicIdList;

}
