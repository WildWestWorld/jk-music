package com.wildwestworld.jkmusic.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/wechat")
public class WechatController {

//    自定义的参数，在config里面，里面放着一些参数，有兴趣可以看一下
    @Resource
    private WxMpService wxMpService;

//    这样写的来源：https://github.com/Wechat-Group/WxJava/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83
    @GetMapping("/auth_url")
    public  String getAuthUrl(@PathParam("redirectUrl") String redirectUrl){
        return wxMpService.getOAuth2Service().buildAuthorizationUrl(redirectUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
    }
    //    这样写的来源：https://github.com/Wechat-Group/WxJava/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83
    @PostMapping("/get_user_info")
    public WxOAuth2UserInfo getUserInfo(@PathParam("code") String code) throws WxErrorException {
        //获得能从资源服务区获取用户信息的token
        WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        //这里官方的文档写错了，现在改正
        WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
        return userInfo;
    }
}
