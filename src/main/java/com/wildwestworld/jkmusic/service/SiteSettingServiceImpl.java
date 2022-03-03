package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.transport.dto.SiteSettingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SiteSettingServiceImpl implements SiteSettingService{

    //从properties拿bucket参数
    @Value("${cos.bucket}")
    private String bucket;


    @Value("${cos.region}")
    private String region;

    private FileService fileService;


    @Override
    public SiteSettingDto getSiteService() {
        SiteSettingDto siteSettingDto = new SiteSettingDto();
        siteSettingDto.setBucket(bucket);
        siteSettingDto.setRegion(region);
        siteSettingDto.setStorage(fileService.getDefaultStorage());
        return siteSettingDto;

    }
}
