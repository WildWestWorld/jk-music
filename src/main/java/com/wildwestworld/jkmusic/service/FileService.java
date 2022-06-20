package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.emuns.Storage;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadRequest;

import java.io.IOException;


public interface FileService {
    FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException;

    FileDto finishUpload(String id) throws IOException;

    Storage getDefaultStorage();
}
