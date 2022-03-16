package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;

import java.io.IOException;

public interface StorageService {
     FileUploadDto initFileUploadCos() throws IOException;

     String getFileUrl(String fileKey);
}
