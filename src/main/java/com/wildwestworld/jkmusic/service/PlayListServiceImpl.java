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
        //?????????????????????state
        PlayListState state = PlayListState.valueOf("?????????");
        playListEntity.setPlayListState(state);

        //???????????????SecurityContextHolder?????????token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //authentication ?????????getName/getPrincipal().toString()
        // ????????? ?????????JWTAuthenticationFilter?????????????????????????????????filter?????????JWTAuthenticationFilter
        //??????????????????????????????username ??????????????????loadUserByUsername ?????????user
        User currentUser = userService.loadUserByUsername(authentication.getPrincipal().toString());

        String currentUserId = currentUser.getId();


        playListEntity.setCreatorId(currentUserId);
        //????????????????????????
        playListMapper.insert(playListEntity);


        if (CollUtil.isNotEmpty(playListCreateRequest.getTagIdList())) {
            playListMapper.batchInsertPlayListTag(playListEntity, playListCreateRequest.getTagIdList());
        }
        //???????????????Dto
        PlayListDto playListDto = playListRepository.playListToDto(playListEntity);
        return playListDto;
    }

    @Override
    @Transactional
    public PlayListDto updatePlayListById(String id, PlayListUpdateRequest playListUpdateRequest) {

        PlayList playList = playListMapper.selectPlayListById(id);
        if(playList == null){
            //?????????????????????  ?????????????????????????????????exception??????
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }

        //??????artistUpdateRequest???Name????????????
        if (StrUtil.isNotEmpty(playListUpdateRequest.getName())){
            //????????????
            playList.setName(playListUpdateRequest.getName());
        }

        //??????artistUpdateRequest???ArtistState????????????
        if (StrUtil.isNotEmpty(playListUpdateRequest.getPlayListState())) {
            //????????????
            //?????????????????????ArtistState
            String playlistState = playListUpdateRequest.getPlayListState();
            //?????????????????????ArtistState???valueOf?????????????????????ArtistState
            PlayListState playListState = PlayListState.valueOf(playlistState);

            playList.setPlayListState(playListState);
        }

        if (StrUtil.isNotEmpty(playListUpdateRequest.getDescription())) {
            playList.setDescription(playListUpdateRequest.getDescription());
        }

        if (StrUtil.isNotEmpty(playListUpdateRequest.getCoverId())) {
            playList.setCoverId(playListUpdateRequest.getCoverId());
        }

        //??????playListUpdateRequest???RecommendFactor????????????
        if (playListUpdateRequest.getRecommendFactor() !=null){
            playList.setRecommendFactor(playListUpdateRequest.getRecommendFactor());
        }

        //??????playListUpdateRequest???Recommended????????????

        if (playListUpdateRequest.getRecommended() !=null){
            playList.setRecommended(playListUpdateRequest.getRecommended());
        }


        //??????playListUpdateRequest???special????????????

        if (playListUpdateRequest.getSpecial() !=null){
            playList.setSpecial(playListUpdateRequest.getSpecial());
        }


//????????????????????????
        if (playListUpdateRequest.getMusicIdList() !=null ) {
            if( CollUtil.isNotEmpty(playListUpdateRequest.getMusicIdList() )) {

                List<String> originIdList;
                if (playList.getMusicList() != null & CollUtil.isNotEmpty(playList.getMusicList())) {
                    //??????1:
                    //??????????????????????????????musicList?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                    List<String> IdList = playList.getMusicList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //?????????Id List - ?????????Id List = ??????List?????????id/?????????????????????IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //????????????????????????Id
                //CollUtil.subtractToList(A,B)???????????????A??????-B???????????????????????????A????????????

                //???????????????Id??????
                List<String> needInsertIdList = CollUtil.subtractToList(playListUpdateRequest.getMusicIdList(), originIdList);


                //???????????????Id??????
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

//????????????????????????
        if (playListUpdateRequest.getTagIdList() !=null ) {
            if( CollUtil.isNotEmpty(playListUpdateRequest.getTagIdList() )) {

                List<String> originIdList;
                if (playList.getTagList() != null & CollUtil.isNotEmpty(playList.getTagList())) {
                    //??????1:
                    //??????????????????????????????musicList?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                    List<String> IdList = playList.getTagList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //?????????Id List - ?????????Id List = ??????List?????????id/?????????????????????IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //????????????????????????Id
                //CollUtil.subtractToList(A,B)???????????????A??????-B???????????????????????????A????????????

                //???????????????Id??????
                List<String> needInsertIdList = CollUtil.subtractToList(playListUpdateRequest.getTagIdList(), originIdList);


                //???????????????Id??????
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


        //??????user
        playListMapper.updateById(playList);



        //????????????user
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
            //?????????????????????  ?????????????????????????????????exception??????
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("?????????");

        playList.setPlayListState(musicState);

        playListMapper.updateById(playList);
    }

    @Override
    public void changePlayListStateToClosed(String id) {
        PlayList playList = playListMapper.selectById(id);
        if(playList == null){
            //?????????????????????  ?????????????????????????????????exception??????
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("?????????");

        playList.setPlayListState(musicState);

        playListMapper.updateById(playList);
    }

    @Override
    public void changePlayListStateToWaited(String id) {

        PlayList playList = playListMapper.selectById(id);
        if(playList == null){
            //?????????????????????  ?????????????????????????????????exception??????
            throw new BizException(BizExceptionType.PlayList_NOT_FOUND);
        }
        PlayListState musicState = PlayListState.valueOf("?????????");

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
