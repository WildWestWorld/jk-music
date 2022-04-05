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

    @NotNull(message = "更新的封面不能为空")
    private String coverId;
}
