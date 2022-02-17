package com.wildwestworld.jkmusic.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatConfig {
//    从spring的配置文件中拿参数
    @Value("${weixin.mp.app-id}")
    private String appId;
    @Value("${weixin.mp.app-secret}")
    private String appSecret;
    @Bean
    WxMpService wxMpService(){
//        config的形式，可以直接注入到我们的WxController里面
        //https://github.com/Wechat-Group/WxJava/wiki/MP_Quick-Start 来源
        //微信的配置
        WxMpDefaultConfigImpl config =new WxMpDefaultConfigImpl();
        config.setAppId(appId);
        config.setSecret(appSecret);

        //配置保存在service里面
        WxMpService wxMpService =new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        return  wxMpService;
    }
}
