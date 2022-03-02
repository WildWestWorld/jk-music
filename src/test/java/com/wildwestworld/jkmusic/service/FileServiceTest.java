package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.transport.dto.FileUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FileServiceTest {

    private FileService fileService;

    @Test
    void initUpload() throws IOException {
        FileUploadRequest fileUploadRequest =new FileUploadRequest();
        fileUploadRequest.setName("测试文件名");
        fileUploadRequest.setExt("mp3");
        fileUploadRequest.setHashKey("111111");
        fileUploadRequest.setSize(30000);

        fileService.initUpload(fileUploadRequest);



    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}