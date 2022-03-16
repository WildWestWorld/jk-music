package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.File;
import com.wildwestworld.jkmusic.mapper.FileMapper;
import com.wildwestworld.jkmusic.service.StorageService;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//该类是为了重写dto，让fileDto多放进去一个属性url
public abstract class FileRepositoryDecorator implements FileRepository {
    @Autowired
    @Qualifier("delegate")
    private FileRepository delegate;

    @Autowired
    private StorageService storageService;

    @Override
    public FileDto fileToDto(File file) {
        FileDto fileDto =delegate.fileToDto(file);
        if(fileDto==null){
            return null;
        }

            String hashKey = fileDto.getHashKey();
            //从cos中获取临时url

            fileDto.setUrl(storageService.getFileUrl(hashKey));

            return fileDto;


    }
}
