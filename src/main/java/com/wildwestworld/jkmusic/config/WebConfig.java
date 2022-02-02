package com.wildwestworld.jkmusic.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

@Configuration
public class WebConfig {
    @Bean
    //密码加密，用于sql里面
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
