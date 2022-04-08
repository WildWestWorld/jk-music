package com.wildwestworld.jkmusic.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.ArtistMapper;
import com.wildwestworld.jkmusic.mapper.PlayListMapper;
import com.wildwestworld.jkmusic.repository.ArtistRepository;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService{
    @Resource
    ArtistMapper artistMapper;
    @Resource
    ArtistRepository artistRepository;
    @Resource
    MusicRepository musicRepository;


    @Override
    public ArtistDto createArtist(ArtistCreateRequest artistCreateRequest) {
        //先给他转化成Entity
        Artist artistEntity = artistRepository.createArtistEntity(artistCreateRequest);
        System.out.println(artistEntity);
        //放入枚举类型的state
        ArtistState state = ArtistState.valueOf("待上架");
        artistEntity.setArtistState(state);
        //将实体类插入数据
        artistMapper.insert(artistEntity);
        //然后转化为Dto
        ArtistDto artistDto = artistRepository.artistToDto(artistEntity);
        return artistDto;
    }


    @Override
    public List<ArtistDto> getArtistList(String searchWord) {


        List<Artist> artistList = artistMapper.getArtistList(searchWord);

        List<ArtistDto> artistDtoList = artistList.stream().map(artistRepository::artistToDto).collect(Collectors.toList());
        return artistDtoList;
    }

    @Override
    public ArtistDto updateArtistById(String id, ArtistUpdateRequest artistUpdateRequest) {
        Artist artist = artistMapper.selectById(id);
        if(artist == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Artist_NOT_FOUND);
        }

        //如果artistUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(artistUpdateRequest.getName())){
            //设置名字
            artist.setName(artistUpdateRequest.getName());
        }

        //如果artistUpdateRequest的ArtistState不是空的
        if (StrUtil.isNotEmpty(artistUpdateRequest.getArtistState())) {
            //设置性别
            //获取文字形式的ArtistState
            String artistState = artistUpdateRequest.getArtistState();
            //利用文字形式的ArtistState和valueOf获取枚举形式的ArtistState
            ArtistState State = ArtistState.valueOf(artistState);

            artist.setArtistState(State);
        }

        //如果userUpdateRequest的Nickname不是空的
        if (StrUtil.isNotEmpty(artistUpdateRequest.getRemark())) {
            //设置昵称
            artist.setRemark(artistUpdateRequest.getRemark());
        }

        //如果userUpdateRequest的FileId不是空的
        if (StrUtil.isNotEmpty(artistUpdateRequest.getPhotoId())) {
            //设置昵称
            artist.setPhotoId(artistUpdateRequest.getPhotoId());
        }

        //如果userUpdateRequest的FileId不是空的
        if (StrUtil.isNotEmpty(artistUpdateRequest.getRecommendFactor().toString())){
            //设置昵称
            artist.setRecommendFactor(artistUpdateRequest.getRecommendFactor());
        }

        if (StrUtil.isNotEmpty(artistUpdateRequest.getRecommended().toString())){
            //设置昵称
            artist.setRecommended(artistUpdateRequest.getRecommended());
        }
        //更新user
        artistMapper.updateById(artist);
        //再次查询user
        Artist updateArtist = artistMapper.selectById(id);

        ArtistDto artistDto = artistRepository.artistToDto(updateArtist);
        return artistDto;
    }

    //删除Music
    @Override
    public void deleteArtistByID(String id) {
        artistMapper.deleteById(id);
    }

    @Override
    public void changeArtistStateToPublic(String id) {
        Artist artist = artistMapper.selectById(id);
        if(artist == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Artist_NOT_FOUND);
        }
        ArtistState artistState = ArtistState.valueOf("已上架");

        artist.setArtistState(artistState);

        artistMapper.updateById(artist);
    }

    @Override
    public void changeArtistStateToClosed(String id) {
        Artist artist = artistMapper.selectById(id);
        if(artist == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Artist_NOT_FOUND);
        }
        ArtistState artistState = ArtistState.valueOf("已下架");

        artist.setArtistState(artistState);

        artistMapper.updateById(artist);
    }

    @Override
    public void changeArtistStateToWaited(String id) {
        Artist artist = artistMapper.selectById(id);
        if(artist == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Artist_NOT_FOUND);
        }
        ArtistState artistState = ArtistState.valueOf("待上架");

        artist.setArtistState(artistState);

        artistMapper.updateById(artist);
    }


    @Override
    public ArtistDto changeToRecommend(String id, ArtistRecommendRequest artistRecommendRequest) {
        Artist artist = artistMapper.selectById(id);
        artist.setRecommended(true);
        Integer recommendFactor = artistRecommendRequest.getRecommendFactor();
        artist.setRecommendFactor(recommendFactor);


        artistMapper.updateById(artist);

        Artist artistAfterUpdate = artistMapper.selectById(id);
        ArtistDto artistDto = artistRepository.artistToDto(artistAfterUpdate);
        return artistDto;
    }


    @Override
    public ArtistDto cancelRecommend(String id) {
        Artist artist = artistMapper.selectById(id);
        artist.setRecommended(false);
        artist.setRecommendFactor(0);


        artistMapper.updateById(artist);

        Artist artistAfterUpdate = artistMapper.selectById(id);
        ArtistDto artistDto = artistRepository.artistToDto(artistAfterUpdate);
        return artistDto;
    }

    @Override
    public IPage<ArtistDto> getArtistPage(Integer pageNum, Integer pageSize, String searchWord) {



        IPage<Artist> artistPage = artistMapper.getPage(new Page<>(pageNum, pageSize), searchWord);
        List<Artist> artistList = artistPage.getRecords();
        List<MusicDto> musicDtoList = new ArrayList<>();

        List<ArtistDto> artistDtoList = artistList.stream().map(artistRepository::artistToDto).collect(Collectors.toList());


//        for (Artist artist : artistList) {
//            List<MusicDto> musicDtoListTest = artist.getMusicList().stream().map(musicRepository::musicToDto).collect(Collectors.toList());
//            musicDtoList=musicDtoListTest;
//        }
//
//        for (ArtistDto artistDto : artistDtoList) {
//            artistDto.setMusicDtoList(musicDtoList);
//        }


        IPage<ArtistDto> artistDtoPage = new Page<>(pageNum, pageSize);
        artistDtoPage.setRecords(artistDtoList);

        artistDtoPage.setCurrent(artistPage.getCurrent());
        artistDtoPage.setTotal(artistPage.getTotal());
        artistDtoPage.setSize(artistPage.getSize());

        return artistDtoPage;
    }
}
