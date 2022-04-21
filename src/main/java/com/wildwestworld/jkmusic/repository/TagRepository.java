package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.vo.AlbumVo;
import com.wildwestworld.jkmusic.transport.vo.TagVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface TagRepository {
    TagDto tagToDto(Tag tag);

    TagVo tagToVo(TagDto tagDto);

    Tag createTagEntity (TagCreateRequest tagCreateRequest);
}
