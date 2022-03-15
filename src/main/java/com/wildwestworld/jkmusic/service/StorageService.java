package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;

import java.io.IOException;

public interface StorageService {
    public FileUploadDto initFileUploadCos() throws IOException;
}
