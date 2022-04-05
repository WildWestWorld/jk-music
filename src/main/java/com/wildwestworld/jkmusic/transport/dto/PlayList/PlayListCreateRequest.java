package com.wildwestworld.jkmusic.transport.dto.PlayList;

import com.wildwestworld.jkmusic.emuns.PlayListState;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Data
public class PlayListCreateRequest {

    @NotBlank(message = "歌单名不能为空")
    private String name;

    private String description;

    @NotNull(message = "请上传封面")
    private String coverId;


}
