package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.mapper.PlayListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PlayListServiceImpl implements PlayListService{
    @Resource
    PlayListMapper playListMapper;


    @Override
    public IPage<PlayList> getPlayListPage(Integer pageNum, Integer pageSize, String searchWord) {


        IPage<PlayList> playListPage = playListMapper.getPage(new Page<>(pageNum, pageSize), searchWord);
        return playListPage;
    }
}
