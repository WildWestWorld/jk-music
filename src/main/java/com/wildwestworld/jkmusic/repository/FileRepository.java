package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.File;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.transport.dto.FileDto;
import com.wildwestworld.jkmusic.transport.dto.FileUploadRequest;
import com.wildwestworld.jkmusic.transport.dto.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.vo.FileVo;
import org.mapstruct.Mapper;

//专门用于File实体类的dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring")
public interface FileRepository {
    File createFileEntity (FileUploadRequest fileUploadRequest);
    FileDto fileToDto(File file);
    FileVo fileToVo(FileDto fileDto);
}
