package com.wildwestworld.jkmusic.transport.vo;

import com.wildwestworld.jkmusic.emuns.Storage;
import lombok.Data;

@Data
public class SiteSettingVo {
    private String bucket;

    private String region;

    private Storage storage;

}
