package com.wildwestworld.jkmusic.config;

import com.github.dreamyoung.mprelation.AutoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//mprelation的配置，让他引入我们的实体类
@Configuration
public class AutoMapperConfig {
    @Bean
    public AutoMapper autoMapper() {
        return new AutoMapper(new String[] {
                "com.wildwestworld.jkmusic.entity" });
    }

}