package com.wildwestworld.jkmusic.transport.dto.Tag;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TagCreateRequest {


    @NotBlank(message = "音乐标签的名字不能为空")
    private String name;


}
