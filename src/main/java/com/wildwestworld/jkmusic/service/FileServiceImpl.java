package com.wildwestworld.jkmusic.service;

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
        //创建File实体，并存入数据库
        File fileEntity = fileRepository.createFileEntity(fileUploadRequest);
        //FileTypeTransformer来自util文件 使用后缀名判断文件类型
        FileType fileTypeFromExt = FileTypeTransformer.getFileTypeFromExt(fileUploadRequest.getExt());

        fileEntity.setStorage(Storage.COS);
        fileEntity.setFileType(fileTypeFromExt);

        System.out.println(fileEntity);
        fileMapper.insert(fileEntity);


        //准备从腾讯云拿数据，通过接口拿取SDS令牌

//        FileUploadDto fileUploadDto= this.storageService.get(storageName).initFileUploadCos();
        //此时的fileUploadDto是装配好配置的dto
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
        fileMapper.updateById(file);
        FileDto fileDto = fileRepository.fileToDto(file);
        return fileDto;
    }

    //后台设置当前的Storage
    public Storage getDefaultStorage(){
        return Storage.COS;
    }


}
