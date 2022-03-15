package com.wildwestworld.jkmusic.transport.dto.Music;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class MusicUpdateRequest {

    @NotBlank
    private String name;

    private String description;

    private String musicState;

    @NotBlank   
    private String fileId;
}
