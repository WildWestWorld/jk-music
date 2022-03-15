package com.wildwestworld.jkmusic.transport.dto.Music;

import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MusicCreateRequest {
    @NotBlank(message = "歌曲名称不能为空")
    private String name;

    private String description;

    private String musicState;

    @NotBlank(message = "歌曲文件/歌曲ID不能为空")
    private String fileId;




}
