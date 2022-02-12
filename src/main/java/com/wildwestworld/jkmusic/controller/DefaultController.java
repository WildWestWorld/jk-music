package com.wildwestworld.jkmusic.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/hello")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DefaultController {
    //get方法，请求hello然后返回字符串hello
    @GetMapping("")
    public String sayHello(){

        return "恭迎宗主回宗";
    }

}
