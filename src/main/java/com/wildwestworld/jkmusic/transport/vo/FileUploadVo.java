package com.wildwestworld.jkmusic.transport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

//参考资料:https://cloud.tencent.com/document/product/436/14048
//用于文件上传的身份验证的dto，有关Cos的
@Data
public class FileUploadVo {
    private String secretId;

    private String secretKey;

    private String sessionToken;

    private String key;

    private String fileId;


    private Long startTime;

    private Long expiredTime;
}
