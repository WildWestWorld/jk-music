package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.mapper.PlayListMapper;
import com.wildwestworld.jkmusic.repository.ArtistRepository;
import com.wildwestworld.jkmusic.repository.PlayListRepository;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListRecommendRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PlayListServiceImpl implements PlayListService{
    @Resource
    PlayListMapper playListMapper;
    @Resource
    PlayListRepository playListRepository;

    @Override
    public PlayListDto changeToRecommend(String id, PlayListRecommendRequest playListRecommendRequest) {

        PlayList playList = playListMapper.selectById(id);
        playList.setRecommended(true);
        Integer recommendFactor = playListRecommendRequest.getRecommendFactor();
        playList.setRecommendFactor(recommendFactor);


        playListMapper.updateById(playList);

        PlayList playListAfterUpdate = playListMapper.selectById(id);
        PlayListDto playListDto = playListRepository.playListToDto(playListAfterUpdate);
        return playListDto;
    }

    @Override
    public PlayListDto cancelRecommend(String id) {

        PlayList playList = playListMapper.selectById(id);
        playList.setRecommended(false);
        playList.setRecommendFactor(0);


        playListMapper.updateById(playList);

        PlayList playListAfterUpdate = playListMapper.selectById(id);
        PlayListDto playListDto = playListRepository.playListToDto(playListAfterUpdate);
        return playListDto;
    }

    @Override
    public IPage<PlayList> getPlayListPage(Integer pageNum, Integer pageSize, String searchWord) {


        IPage<PlayList> playListPage = playListMapper.getPage(new Page<>(pageNum, pageSize), searchWord);
        return playListPage;
    }


}
