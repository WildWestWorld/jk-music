package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.emuns.PlayListState;
import com.wildwestworld.jkmusic.entity.*;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.PlayListMapper;
import com.wildwestworld.jkmusic.repository.ArtistRepository;
import com.wildwestworld.jkmusic.repository.PlayListRepository;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListUpdateRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayListServiceImpl implements PlayListService{
    @Resource
    PlayListMapper playListMapper;
    @Resource
    PlayListRepository playListRepository;

    @Resource
    UserService userService;


    @Override
    public List<PlayListDto> getPlayList(String searchWord) {
//        LambdaQueryWrapper<PlayList> wrapper = Wrappers.<PlayList>lambdaQuery();
//        wrapper.like(PlayList::getName,searchWord);

        List<PlayList> playList = playListMapper.getPlayList(searchWord);

        List<PlayListDto> playListDtoList = playList.stream().map(playListRepository::playListToDto).collect(Collectors.toList());
        return playListDtoList;
    }

    @Override
    public List<PlayListDto> getPlaySelectionList(String searchWord) {
//        LambdaQueryWrapper<PlayList> wrapper = Wrappers.<PlayList>lambdaQuery();
//        wrapper.like(PlayList::getName,searchWord);

        List<PlayList> playList = playListMapper.getPlaySelectionList(searchWord);

        List<PlayListDto> playListDtoList = playList.stream().map(playListRepository::playListToDto).collect(Collectors.toList());
        return playListDtoList;
    }


    @Override
    public PlayListDto getPlayListById(String id) {
        PlayList playList = playListMapper.selectPlayListById(id);
        PlayListDto playListDto = playListRepository.playListToDto(playList);
        return playListDto;
    }

    @Override
    @Transactional
    public PlayListDto createPlayList(PlayListCreateRequest playListCreateRequest) {
        PlayList playListEntity = playListRepository.createPlayListEntity(playListCreateRequest);
        System.out.println(playListEntity);
        //放入枚举类型的state
        PlayListState state = PlayListState.valueOf("已上架");
        playListEntity.setPlayListState(state);

        //获得储存在SecurityContextHolder里面的token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //authentication 可以用getName/getPrincipal().toString()
        // 是因为 我们在JWTAuthenticationFilter传入进去的，具体可以看filter里面的JWTAuthenticationFilter
        //然后我们利用获得到的username 和自定义方法loadUserByUsername 获取到user
        User currentUser = userService.loadUserByUsername(authentication.getPrincipal().toString());

        String currentUserId = currentUser.getId();


        playListEntity.setCreatorId(currentUserId);
        //将实体类插入数据
        playListMapper.insert(playListEntity);


        if (CollUtil.isNotEmpty(playListCreateRequest.getTagIdList())) {
            playListMapper.batchInsertPlayListTag(playListEntity, playListCreateRequest.getTagIdList());
        }
        //然后转化为Dto
        PlayListDto playListDto = playListRepository.playListToDto(playListEntity);
        return playListDto;
    }

    @Override
    @Transactional
    public PlayListDto updatePlayListById(String id, PlayListUpdateRequest playListUpdateRequest) {

        PlayList playList = playListMapper.selectPlayListById(id);
        if(playList == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }

        //如果artistUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(playListUpdateRequest.getName())){
            //设置名字
            playList.setName(playListUpdateRequest.getName());
        }

        //如果artistUpdateRequest的ArtistState不是空的
        if (StrUtil.isNotEmpty(playListUpdateRequest.getPlayListState())) {
            //设置性别
            //获取文字形式的ArtistState
            String playlistState = playListUpdateRequest.getPlayListState();
            //利用文字形式的ArtistState和valueOf获取枚举形式的ArtistState
            PlayListState playListState = PlayListState.valueOf(playlistState);

            playList.setPlayListState(playListState);
        }

        if (StrUtil.isNotEmpty(playListUpdateRequest.getDescription())) {
            playList.setDescription(playListUpdateRequest.getDescription());
        }

        if (StrUtil.isNotEmpty(playListUpdateRequest.getCoverId())) {
            playList.setCoverId(playListUpdateRequest.getCoverId());
        }

        //如果playListUpdateRequest的RecommendFactor不是空的
        if (playListUpdateRequest.getRecommendFactor() !=null){
            playList.setRecommendFactor(playListUpdateRequest.getRecommendFactor());
        }

        //如果playListUpdateRequest的Recommended不是空的

        if (playListUpdateRequest.getRecommended() !=null){
            playList.setRecommended(playListUpdateRequest.getRecommended());
        }


        //如果playListUpdateRequest的special不是空的

        if (playListUpdateRequest.getSpecial() !=null){
            playList.setSpecial(playListUpdateRequest.getSpecial());
        }


//音乐与歌单的关系
        if (playListUpdateRequest.getMusicIdList() !=null ) {
            if( CollUtil.isNotEmpty(playListUpdateRequest.getMusicIdList() )) {

                List<String> originIdList;
                if (playList.getMusicList() != null & CollUtil.isNotEmpty(playList.getMusicList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = playList.getMusicList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(playListUpdateRequest.getMusicIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, playListUpdateRequest.getMusicIdList());

                if (needDeleteIdList.size() != 0) {
                    playListMapper.batchDeletePlayListMusicById(playList, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    playListMapper.batchInsertPlayListMusic(playList, needInsertIdList);
                }

            }else{
                if (playList.getMusicList() != null  & CollUtil.isNotEmpty(playList.getMusicList())) {
                    playListMapper.deleteAllPlayListMusicById(playList);
                }
            }
        }

//歌单与标签的关系
        if (playListUpdateRequest.getTagIdList() !=null ) {
            if( CollUtil.isNotEmpty(playListUpdateRequest.getTagIdList() )) {

                List<String> originIdList;
                if (playList.getTagList() != null & CollUtil.isNotEmpty(playList.getTagList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = playList.getTagList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(playListUpdateRequest.getTagIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, playListUpdateRequest.getTagIdList());

                if (needDeleteIdList.size() != 0) {
                    playListMapper.batchDeletePlayListTagById(playList, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    playListMapper.batchInsertPlayListTag(playList, needInsertIdList);
                }

            }else{
                if (playList.getTagList() != null  & CollUtil.isNotEmpty(playList.getTagList())) {
                    playListMapper.deleteAllPlayListTagById(playList);
                }
            }
        }


        //更新user
        playListMapper.updateById(playList);



        //再次查询user
        PlayList playListAfterUpdate = playListMapper.selectPlayListById(id);

        PlayListDto playListDto = playListRepository.playListToDto(playListAfterUpdate);
        return playListDto;
    }


    @Override
    @Transactional
    public void deletePlayListByID(String id) {
        PlayList playList = playListMapper.selectPlayListById(id);
        if (playList.getMusicList() !=null & !CollUtil.isEmpty(playList.getMusicList())) {
            playListMapper.deleteAllPlayListMusicById(playList);
        }
        if (playList.getTagList() !=null & !CollUtil.isEmpty(playList.getTagList())) {
            playListMapper.deleteAllPlayListTagById(playList);
        }

        playListMapper.deleteById(id);
    }

    @Override
    public void changePlayListStateToPublic(String id) {
        PlayList playList = playListMapper.selectById(id);
        if(playList == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("已上架");

        playList.setPlayListState(musicState);

        playListMapper.updateById(playList);
    }

    @Override
    public void changePlayListStateToClosed(String id) {
        PlayList playList = playListMapper.selectById(id);
        if(playList == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("已下架");

        playList.setPlayListState(musicState);

        playListMapper.updateById(playList);
    }

    @Override
    public void changePlayListStateToWaited(String id) {

        PlayList playList = playListMapper.selectById(id);
        if(playList == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("待上架");

        playList.setPlayListState(musicState);

        playListMapper.updateById(playList);
    }

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
    public IPage<PlayListDto> getPlayListPage(Integer pageNum, Integer pageSize, String searchWord,Boolean orderRecommend) {
        IPage<PlayList> playListPage = playListMapper.getPage(new Page<>(pageNum, pageSize), searchWord,orderRecommend);

        List<PlayList> playList = playListPage.getRecords();

        List<PlayListDto> playListDtoList = playList.stream().map(playListRepository::playListToDto).collect(Collectors.toList());


        IPage<PlayListDto> playListDtoPage = new Page<>(pageNum, pageSize);
        playListDtoPage.setRecords(playListDtoList);

        playListDtoPage.setCurrent(playListPage.getCurrent());
        playListDtoPage.setTotal(playListPage.getTotal());
        playListDtoPage.setSize(playListDtoPage.getSize());

        return playListDtoPage;
    }


}
