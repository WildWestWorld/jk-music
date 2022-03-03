package com.wildwestworld.jkmusic.transport.dto;

import com.wildwestworld.jkmusic.emuns.Storage;
import lombok.Data;

@Data
public class SiteSettingDto {
    private String bucket;

    private String region;

    private Storage storage;

}
