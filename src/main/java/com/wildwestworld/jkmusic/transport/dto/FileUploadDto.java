package com.wildwestworld.jkmusic.transport.dto;

import lombok.Data;

import java.util.Date;

//参考资料:https://cloud.tencent.com/document/product/436/14048
//用于文件上传的身份验证的dto，有关Cos的
@Data
public class FileUploadDto {
//    临时密钥 Id，可用于计算签名
    private String secretId;
//    临时密钥 Key，可用于计算签名
    private String secretKey;
//请求时需要用的 token 字符串，最终请求 COS API 时，需要放在 Header 的 x-cos-security-token 字段
    private String sessionToken;

    //后端确认身份后就传入这个key到前端去
    private String hashKey;

    private String fileId;




    private Long startTime;

    private Long expiredTime;

}
