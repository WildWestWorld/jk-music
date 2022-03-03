package com.wildwestworld.jkmusic.repository;


import com.wildwestworld.jkmusic.transport.dto.SiteSettingDto;
import com.wildwestworld.jkmusic.transport.vo.SiteSettingVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteSettingRepository {
    SiteSettingVo SiteSettingToVo (SiteSettingDto siteSettingVo);
}
