package com.wildwestworld.jkmusic.transport.dto.Token;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
//用于请求 用于POST
//文件用于token 传输
@Data
public class TokenCreateRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 64,message = "用户名长度应该在4个字符到64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 64,message = "密码长度应该在4个字符到64个字符之间")
    private String password;
}
