package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.MusicUpdateRequest;
import com.wildwestworld.jkmusic.utils.Result;

import java.util.List;

public interface MusicService {
   void deleteMusicByID(String id);


    MusicDto createMusic(MusicCreateRequest musicCreateRequest);

    MusicDto updateMusicById(String id, MusicUpdateRequest musicUpdateRequest);

    List<MusicDto> getMusicList();

    void changeMusicStateToPublic(String id);

    void changeMusicStateToClosed(String id);

    void changeMusicStateToWaited(String id);

    IPage<MusicDto> getMusicPage(Integer pageNum, Integer pageSize, String searchWord);
}

