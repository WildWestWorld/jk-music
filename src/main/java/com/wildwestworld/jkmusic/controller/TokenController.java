package com.wildwestworld.jkmusic.controller;

import com.wildwestworld.jkmusic.service.UserService;
import com.wildwestworld.jkmusic.transport.dto.Token.TokenCreateRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


//该页面用于生成token
@RestController
@RequestMapping("/tokens")
@CrossOrigin
public class TokenController {

    @Resource
    UserService userService;


    @PostMapping
    public String createToken(@RequestBody TokenCreateRequest tokenCreateRequest){
        return userService.createToken(tokenCreateRequest);
    }

}
