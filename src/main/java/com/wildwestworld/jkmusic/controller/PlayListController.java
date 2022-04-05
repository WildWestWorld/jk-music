package com.wildwestworld.jkmusic.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.repository.PlayListRepository;
import com.wildwestworld.jkmusic.service.PlayListService;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListRecommendRequest;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.PlayListVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/playlist")
public class PlayListController {
    @Resource
    PlayListService playListService;

    @Resource
    PlayListRepository playListRepository;

    //根据ID修改artist的recommend为真
    @PostMapping("/{id}/recommend")
    public PlayListVo recommend(@PathVariable String id, @Validated @RequestBody PlayListRecommendRequest playListRecommendRequest){
        PlayListDto playListDto = playListService.changeToRecommend(id, playListRecommendRequest);
        PlayListVo playListVo = playListRepository.playListToVo(playListDto);

        return playListVo;
    }

    //根据ID修改artist的recommend为真
    @PostMapping("/{id}/cancel_recommend")
    public PlayListVo cancelRecommend(@PathVariable String id){
        PlayListDto playListDto = playListService.cancelRecommend(id);
        PlayListVo playListVo = playListRepository.playListToVo(playListDto);

        return playListVo;
    }


    @GetMapping("/page")
    public IPage<PlayList> getPlayListPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String searchWord
                                    ){
        IPage<PlayList> playListPage = playListService.getPlayListPage(pageNum, pageSize, searchWord);

        return playListPage;

    }



}
