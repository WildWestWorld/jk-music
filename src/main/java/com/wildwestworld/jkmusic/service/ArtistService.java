package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;

import java.util.List;

public interface ArtistService {
    IPage<ArtistDto> getArtistPage(Integer pageNum, Integer pageSize, String searchWord,Boolean orderRecommend);

    List<ArtistDto> getArtistList(String searchWord);

    List<ArtistDto> getArtistSelectionList(String searchWord);


    ArtistDto createArtist(ArtistCreateRequest artistCreateRequest);

    ArtistDto updateArtistById(String id, ArtistUpdateRequest artistUpdateRequest);

    ArtistDto changeToRecommend(String id, ArtistRecommendRequest artistRecommendRequest);

    ArtistDto cancelRecommend(String id);

    void deleteArtistByID(String id);

    void changeArtistStateToPublic(String id);

    void changeArtistStateToClosed(String id);

    void changeArtistStateToWaited(String id);

}
