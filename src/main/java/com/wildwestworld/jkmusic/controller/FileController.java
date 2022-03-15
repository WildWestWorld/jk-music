package com.wildwestworld.jkmusic.controller;


import com.wildwestworld.jkmusic.repository.FileRepository;
import com.wildwestworld.jkmusic.repository.FileUploadRepository;
import com.wildwestworld.jkmusic.service.FileService;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileUploadRequest;
import com.wildwestworld.jkmusic.transport.vo.FileUploadVo;
import com.wildwestworld.jkmusic.transport.vo.FileVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private FileUploadRepository fileUploadRepository;
    @Resource
    private FileRepository fileRepository;

    @PostMapping("/upload_init")
    public FileUploadVo initUpload(@Validated @RequestBody FileUploadRequest fileUploadRequest) throws IOException {
        FileUploadDto fileUploadDto = fileService.initUpload(fileUploadRequest);
        return fileUploadRepository.FileUploadToVo(fileUploadDto);

    }

    @PostMapping("/{id}/upload_finish")
    public FileVo finishUpload(@PathVariable String id){
        FileDto fileDto = fileService.finishUpload(id);
        FileVo fileVo = fileRepository.fileToVo(fileDto);
        return fileVo;
    }
}
