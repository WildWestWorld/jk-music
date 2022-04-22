package com.wildwestworld.jkmusic.transport.dto.Music;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MusicUpdateRequest {

    @NotBlank(message = "更新的歌曲名称不能为空")
    private String name;

    private String description;

    private String musicState;

    private String photoId;

    private List<String> albumIdList;

    private List<String> tagIdList;


    @NotBlank(message = "更新的歌曲文件/歌曲ID不能为空")
    private String fileId;
    @NotNull(message = "更新的歌手不能为空")
    private List<String> artistIdList;
}
