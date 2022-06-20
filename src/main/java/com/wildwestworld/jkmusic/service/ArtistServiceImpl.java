package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
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
import org.springframework.transaction.annotation.Transactional;

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
        ArtistState state = ArtistState.valueOf("已上架");
        artistEntity.setArtistState(state);
        //将实体类插入数据
        artistMapper.insert(artistEntity);


        if (CollUtil.isNotEmpty(artistCreateRequest.getMusicIdList())) {
            artistMapper.batchInsertArtistMusic(artistEntity, artistCreateRequest.getMusicIdList());
        }

        if (CollUtil.isNotEmpty(artistCreateRequest.getAlbumIdList())) {
            artistMapper.batchInsertArtistAlbum(artistEntity, artistCreateRequest.getAlbumIdList());
        }

        //然后转化为Dto
        ArtistDto artistDto = artistRepository.artistToDto(artistEntity);
        return artistDto;
    }


    @Override
    public ArtistDto getArtistById(String id) {
        Artist artist = artistMapper.selectArtistById(id);
        ArtistDto artistDto = artistRepository.artistToDto(artist);
        return artistDto;
    }

    @Override
    public List<ArtistDto> getArtistList(String searchWord) {


        List<Artist> artistList = artistMapper.getArtistList(searchWord);

        List<ArtistDto> artistDtoList = artistList.stream().map(artistRepository::artistToDto).collect(Collectors.toList());
        return artistDtoList;
    }

    @Override
    public List<ArtistDto> getArtistSelectionList(String searchWord) {
        List<Artist> artistList = artistMapper.getArtistSelectionList(searchWord);

        List<ArtistDto> artistDtoList = artistList.stream().map(artistRepository::artistToDto).collect(Collectors.toList());
        return artistDtoList;
    }

    @Override
    @Transactional
    public ArtistDto updateArtistById(String id, ArtistUpdateRequest artistUpdateRequest) {
        Artist artist = artistMapper.selectArtistById(id);
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
            artist.setPhotoId(artistUpdateRequest.getPhotoId());
        }

        //如果userUpdateRequest的RecommendFactor不是空的
        if (artistUpdateRequest.getRecommendFactor() !=null){
            artist.setRecommendFactor(artistUpdateRequest.getRecommendFactor());
        }
        //如果userUpdateRequest的Recommended不是空的

        if (artistUpdateRequest.getRecommended() !=null){
            artist.setRecommended(artistUpdateRequest.getRecommended());
        }



        //更新艺人与音乐的关系
        if (artistUpdateRequest.getMusicIdList() != null ) {
            if (CollUtil.isNotEmpty(artistUpdateRequest.getMusicIdList())) {
                List<String> originIdList;
                if (artist.getMusicList() != null & CollUtil.isNotEmpty(artist.getMusicList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = artist.getMusicList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(artistUpdateRequest.getMusicIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, artistUpdateRequest.getMusicIdList());


                if (needDeleteIdList.size() != 0) {
                    artistMapper.batchDeleteByIdFromArtistMusic(artist, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    artistMapper.batchInsertArtistMusic(artist, needInsertIdList);
                }
            }else{

                if (artist.getMusicList() != null & CollUtil.isNotEmpty(artist.getMusicList())) {
                    artistMapper.deleteAllArtistMusicById(artist);
                }
            }

        }



//更新艺人与专辑的关系
        if (artistUpdateRequest.getAlbumIdList() != null ) {
            if (CollUtil.isNotEmpty(artistUpdateRequest.getAlbumIdList())) {
                List<String> originIdList;
                if (artist.getAlbumList() != null & CollUtil.isNotEmpty(artist.getAlbumList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = artist.getAlbumList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(artistUpdateRequest.getAlbumIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, artistUpdateRequest.getAlbumIdList());

                if (needDeleteIdList.size() != 0) {
                    artistMapper.batchDeleteArtistAlbumById(artist, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    artistMapper.batchInsertArtistAlbum(artist, needInsertIdList);
                }


            }else{
                if (artist.getAlbumList() != null & CollUtil.isNotEmpty(artist.getAlbumList())) {
                    artistMapper.deleteAllArtistAlbumById(artist);
                }
            }
        }





        //更新user
        artistMapper.updateById(artist);
        //再次查询user
        Artist updateArtist = artistMapper.selectArtistById(id);

        ArtistDto artistDto = artistRepository.artistToDto(updateArtist);
        return artistDto;
    }

    //删除Music
    @Override
    @Transactional

    public void deleteArtistByID(String id) {

        Artist artist = artistMapper.selectArtistById(id);

        if (artist.getMusicList() !=null & !CollUtil.isEmpty(artist.getMusicList()) ) {
            artistMapper.deleteAllArtistMusicById(artist);
        }

        if (artist.getAlbumList()!=null & !CollUtil.isEmpty(artist.getAlbumList()) ) {
            artistMapper.deleteAllArtistAlbumById(artist);
        }

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
    public IPage<ArtistDto> getArtistPage(Integer pageNum, Integer pageSize, String searchWord,Boolean orderRecommend) {



        IPage<Artist> artistPage = artistMapper.getPage(new Page<>(pageNum, pageSize), searchWord,orderRecommend);
        List<Artist> artistList = artistPage.getRecords();

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
