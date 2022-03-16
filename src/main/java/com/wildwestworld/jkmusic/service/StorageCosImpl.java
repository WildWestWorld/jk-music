package com.wildwestworld.jkmusic.service;



import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public String getFileUrl(String fileKey) {
        //    方法来源：https://cloud.tencent.com/document/product/436/35217
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
// 详细代码参见本页：简单操作 -> 创建 COSClient
        //createCOSClient就在下面
        COSClient cosClient = createCOSClient();

// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = bucket;
// 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = fileKey;

// 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
// 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

// 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<String, String>();
//        params.put("param1", "value1");

// 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("header1", "value1");

// 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;

        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        //将Url转化为字符串形式
        String StringUrl=url.toString();
        System.out.println(url.toString());

// 确认本进程不再使用 cosClient 实例之后，关闭之
        cosClient.shutdown();
        return StringUrl;
    }

//    方法来源：https://cloud.tencent.com/document/product/436/35217
    //生成腾讯的临时密匙，用于获取文件的url
    // 创建 COSClient 实例，这个实例用来后续调用请求
    COSClient createCOSClient() {

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);


        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(region));

        // 设置请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 以下的设置，是可选的：

        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30*1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30*1000);



        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

}
