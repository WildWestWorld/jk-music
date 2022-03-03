package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.emuns.Storage;
import com.wildwestworld.jkmusic.transport.dto.FileDto;
import com.wildwestworld.jkmusic.transport.dto.FileUploadDto;
import com.wildwestworld.jkmusic.transport.dto.FileUploadRequest;
import com.wildwestworld.jkmusic.transport.vo.FileVo;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface FileService {
    FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException;

    FileDto finishUpload(String id);

    Storage getDefaultStorage();
}
