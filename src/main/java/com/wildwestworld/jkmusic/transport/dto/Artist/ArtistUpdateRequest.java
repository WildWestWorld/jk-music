package com.wildwestworld.jkmusic.transport.dto.Artist;

import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ArtistUpdateRequest {
    @NotBlank(message = "更新的歌手名称不能为空")
    private String name;

    private String remark;

    private String ArtistState;

    @NotBlank(message = "更新的封面文件/封面ID不能为空")
    private String fileId;
}
