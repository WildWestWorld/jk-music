package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagUpdateRequest;

import java.util.List;

public interface TagService {
    IPage<TagDto> getTagPage(Integer pageNum, Integer pageSize, String searchWord);

    List<TagDto> getTagList(String searchWord);

    TagDto createTag(TagCreateRequest tagCreateRequest);

    TagDto updateTagById(String id, TagUpdateRequest tagUpdateRequest);

    void deleteTagByID(String id);

}
