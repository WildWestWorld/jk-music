package com.wildwestworld.jkmusic.transport.dto.Tag;

import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class TagCreateRequest {


    @NotBlank(message = "音乐标签的名字不能为空")
    private String name;

    private List<String> musicIdList;
}
