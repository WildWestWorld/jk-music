package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.transport.dto.File.FileUploadDto;
import com.wildwestworld.jkmusic.transport.vo.FileUploadVo;
import org.mapstruct.Mapper;

//专门用于File实体类的dto 和vo 的转化，和与sql语句无关的方法
@Mapper(componentModel = "spring")
public interface FileUploadRepository {
    FileUploadVo FileUploadToVo (FileUploadDto fileUploadDto);

}
