package com.wildwestworld.jkmusic.transport.dto.User;

import com.wildwestworld.jkmusic.emuns.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//用于请求 用于POST
//用于生成账号
@Data
public class UserCreateByRequest {
// @NotBlank 来自于spring-boot-starter-validation
//需要使用@Validated 配套
// @Validated放在使用该类的Controller层的方法上
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 64,message = "用户名长度应该在4个字符到64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 64,message = "密码长度应该在4个字符到64个字符之间")
    private String password;
    private String nickname;
    private String gender;
}
