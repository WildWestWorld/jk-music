package com.wildwestworld.jkmusic.transport.dto.Tag;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagUpdateRequest {
    @NotBlank(message = "音乐标签更新的名字不能为空")
    private String name;
}
