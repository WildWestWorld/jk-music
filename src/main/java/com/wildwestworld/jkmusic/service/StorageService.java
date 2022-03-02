package com.wildwestworld.jkmusic.service;

import com.wildwestworld.jkmusic.transport.dto.FileUploadDto;
import org.apache.tomcat.util.http.fileupload.FileUpload;

import java.io.IOException;

public interface StorageService {
    public FileUploadDto initFileUploadCos() throws IOException;
}
