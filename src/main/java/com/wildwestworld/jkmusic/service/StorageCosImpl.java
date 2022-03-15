package com.wildwestworld.jkmusic.service;



import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TreeMap;
//初始化cos的
@Service("Cors")
public class StorageCosImpl implements StorageService{
    //从properties拿bucket参数
    @Value("${cos.bucket}")
    private String bucket;

    @Value("${cos.secret-id}")
    private String secretId;

    @Value("${cos.secret-key}")
    private String secretKey;

    @Value("${cos.region}")
    private String region;

    @Override
    public FileUploadDto initFileUploadCos() throws IOException {
        try {

            //此时我们就请求了腾讯的服务器，下面就是把请求后的数据给放进fileUploadDto里面
            Response response = CosStsClient.getCredential(getCosStsConfig());


            FileUploadDto fileUploadDto = new FileUploadDto();

            fileUploadDto.setSecretId(response.credentials.tmpSecretId);
            fileUploadDto.setSecretKey(response.credentials.tmpSecretKey);
            fileUploadDto.setSessionToken(response.credentials.sessionToken);

            fileUploadDto.setStartTime(response.startTime);
            fileUploadDto.setExpiredTime(response.expiredTime);

            return fileUploadDto;
        }catch (Exception e){
            e.printStackTrace();
           throw new BizException(BizExceptionType.INNER_ERROR);

        }
    }

    //配置cosConfig
    //写法来自腾讯云
    //参考资料:https://cloud.tencent.com/document/product/436/14048
    private TreeMap<String, Object> getCosStsConfig() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        config.put("secretId", secretId);
        config.put("secretKey", secretKey);

        // 临时密钥有效时长，单位是秒
        config.put("durationSeconds", 1800);
        config.put("allowPrefixes", new String[]{
                "*"
        });
        config.put("bucket", bucket);
        config.put("region", region);
        String[] allowActions = new String[]{
                // 简单上传
                "name/cos:PutObject",
                "name/cos:PostObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);
        return config;
    }

}
