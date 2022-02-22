package com.wildwestworld.jkmusic.controller;


import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.service.MusicService;
import com.wildwestworld.jkmusic.transport.dto.*;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Resource
    private MusicService musicService;

    @Resource
    private MusicRepository musicRepository;

    @GetMapping
    public List<MusicVo> getMusicList(){

        List<MusicDto> musicList = musicService.getMusicList();
        List<MusicVo> musicVoList = musicList.stream().map(musicRepository::musicToVo).collect(Collectors.toList());
        return musicVoList;
    }




    @PostMapping
    public MusicVo createMusic(@Validated @RequestBody MusicCreateRequest musicCreateRequest){
        MusicDto musicDto = musicService.createMusic(musicCreateRequest);

        MusicVo musicVo = musicRepository.musicToVo(musicDto);

        return musicVo;
    }
    @PutMapping("/{id}")
    public MusicVo updateMusic(@Validated @PathVariable String id, @RequestBody MusicUpdateRequest musicUpdateRequest){
        MusicDto musicDto = musicService.updateMusicById(id, musicUpdateRequest);
        MusicVo musicVo = musicRepository.musicToVo(musicDto);
        return musicVo;
    }

    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    Result<?> deleteMusicByID(@PathVariable String id){
        musicService.deleteMusicByID(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/pubilc")
    Result<?> changeMusicStateToPublic(@PathVariable String id){
        musicService.changeMusicStateToPublic(id);
        return Result.success();
    }

    //根据ID修改music的state改为已下架状态
    @PostMapping("/{id}/closed")
    Result<?> changeMusicStateToClosed(@PathVariable String id){
        musicService.changeMusicStateToClosed(id);
        return Result.success();
    }

    //根据ID修改music的state改为已下架状态
    @PostMapping("/{id}/waited")
    Result<?> changeMusicStateToWaited(@PathVariable String id){
        musicService.changeMusicStateToWaited(id);
        return Result.success();
    }
}
