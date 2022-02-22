package com.wildwestworld.jkmusic.transport.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MusicCreateRequest {
    @NotBlank(message = "歌曲名称不能为空")
    private String name;


    private String description;

}
