package com.wildwestworld.jkmusic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.wildwestworld.jkmusic.mapper")
public class JkMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(JkMusicApplication.class, args);
    }

}
