package com.wildwestworld.jkmusic.transport.dto.File;

import lombok.Data;

import javax.validation.constraints.NotBlank;
// 前端发送过来的，只是说明了一些简单的文件情况
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
