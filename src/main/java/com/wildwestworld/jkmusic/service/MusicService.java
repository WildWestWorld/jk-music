package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;

import java.util.List;

public interface MusicService {
   void deleteMusicByID(String id);


    MusicDto createMusic(MusicCreateRequest musicCreateRequest);

    MusicDto updateMusicById(String id, MusicUpdateRequest musicUpdateRequest);

    List<MusicDto> getMusicList(String searchWord);

    void changeMusicStateToPublic(String id);

    void changeMusicStateToClosed(String id);

    void changeMusicStateToWaited(String id);

    IPage<MusicDto> getMusicPage(Integer pageNum, Integer pageSize, String searchWord);
}

