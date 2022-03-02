package com.wildwestworld.jkmusic.service;

import com.sun.xml.internal.ws.api.model.ExceptionType;
import com.wildwestworld.jkmusic.emuns.FileStatus;
import com.wildwestworld.jkmusic.emuns.FileType;
import com.wildwestworld.jkmusic.emuns.Storage;
import com.wildwestworld.jkmusic.entity.File;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.FileMapper;
import com.wildwestworld.jkmusic.repository.FileRepository;
import com.wildwestworld.jkmusic.transport.dto.FileDto;
import com.wildwestworld.jkmusic.transport.dto.FileUploadDto;
import com.wildwestworld.jkmusic.transport.dto.FileUploadRequest;
import com.wildwestworld.jkmusic.utils.FileTypeTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService{
//    @Resource
//    private Map<String,StorageService> storageService;

    @Resource
    private StorageService storageService;
    @Resource
    private FileRepository fileRepository;

    @Resource
    private FileMapper fileMapper;

    @Override
    //为什么要加事务Transactional ，因为我们有两个命令需要执行或者说操作数据库，若是有一条执行不了可以回滚
    @Transactional
    public FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException {
        //创建File实体
        File fileEntity = fileRepository.createFileEntity(fileUploadRequest);
        //FileTypeTransformer来自util文件
        FileType fileTypeFromExt = FileTypeTransformer.getFileTypeFromExt(fileUploadRequest.getExt());

        fileEntity.setStorage(Storage.COS);
        fileEntity.setFileType(fileTypeFromExt);

        System.out.println(fileEntity);
        fileMapper.insert(fileEntity);


        //通过接口拿取SDS令牌
        String storageName = getDefaultStorage().name();


//        FileUploadDto fileUploadDto= this.storageService.get(storageName).initFileUploadCos();
        FileUploadDto fileUploadDto= storageService.initFileUploadCos();

        fileUploadDto.setHashKey(fileEntity.getHashKey());
        fileUploadDto.setFileId(fileEntity.getId());
        return fileUploadDto;
    }

    @Override
    public FileDto finishUpload(String id) {
        File file = fileMapper.selectById(id);
        if(file == null){
            throw new BizException(BizExceptionType.File_NOT_FOUND);
        }
        file.setFileStatus(FileStatus.UPLOADED);
        FileDto fileDto = fileRepository.fileToDto(file);
        return fileDto;
    }

    //后台设置当前的Storage
    private Storage getDefaultStorage(){
        return Storage.COS;
    }


}
