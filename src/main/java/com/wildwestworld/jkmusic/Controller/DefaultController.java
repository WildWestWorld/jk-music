package com.wildwestworld.jkmusic.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DefaultController {
    //get方法，请求hello然后返回字符串hello
    @GetMapping("")
    public String sayHello(){
        return "恭迎宗主回宗";
    }

}
