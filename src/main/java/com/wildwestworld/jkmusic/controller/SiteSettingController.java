package com.wildwestworld.jkmusic.controller;

import com.wildwestworld.jkmusic.repository.SiteSettingRepository;
import com.wildwestworld.jkmusic.service.SiteSettingService;
import com.wildwestworld.jkmusic.transport.dto.SiteSettingDto;
import com.wildwestworld.jkmusic.transport.vo.SiteSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//用户获取站点基本信息
@RestController
@RequestMapping("/setting")
public class SiteSettingController {
    @Resource
    private SiteSettingService siteSettingService;
    @Resource
    private SiteSettingRepository siteSettingRepository;

    @GetMapping("/site")
    public SiteSettingVo getSiteSetting(){
        SiteSettingDto siteSettingDto = siteSettingService.getSiteService();
        SiteSettingVo siteSettingVo = siteSettingRepository.SiteSettingToVo(siteSettingDto);
        return siteSettingVo;
    }


}
