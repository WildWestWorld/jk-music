package com.wildwestworld.jkmusic.transport.dto.Artist;

import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ArtistCreateRequest {
    @NotBlank(message = "歌手名称不能为空")
    private String name;

    private String remark;

    private FileDto photo;

    @NotBlank(message = "封面文件/封面ID不能为空")
    private String photoId;
}
