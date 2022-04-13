package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;

import java.util.List;

public interface AlbumService {
//    IPage<ArtistDto> getArtistPage(Integer pageNum, Integer pageSize, String searchWord, Boolean orderRecommend);
//
    List<AlbumDto> getAlbumList(String searchWord);


      AlbumDto createAlbum(AlbumCreateRequest albumCreateRequest);
//
      AlbumDto updateAlbumById(String id, AlbumUpdateRequest albumUpdateRequest);
//
//    ArtistDto changeToRecommend(String id, ArtistRecommendRequest artistRecommendRequest);
//
//    ArtistDto cancelRecommend(String id);
//
      void deleteAlbumByID(String id);
//
//    void changeArtistStateToPublic(String id);
//
//    void changeArtistStateToClosed(String id);
//
//    void changeArtistStateToWaited(String id);
}
