package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.mapper.ArtistMapper;
import com.wildwestworld.jkmusic.mapper.PlayListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArtistServiceImpl implements ArtistService{
    @Resource
    ArtistMapper artistMapper;
    @Override
    public IPage<Artist> getArtistPage(Integer pageNum, Integer pageSize, String searchWord) {
        IPage<Artist> artistPage = artistMapper.getPage(new Page<>(pageNum, pageSize), searchWord);

        return artistPage;
    }
}
