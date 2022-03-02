package com.wildwestworld.jkmusic.transport.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class FileUploadRequest {
    @NotBlank(message = "文件名不为空")
    private String name;

    //文件的大小
    private Integer size;

    @NotBlank(message = "后缀名不为空")
    private String ext;

    @NotBlank(message = "密匙hashKey不为空")
    private String hashKey;
}
