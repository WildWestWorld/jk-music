package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;

import java.util.List;

public interface AlbumService {
    IPage<AlbumDto> getAlbumPage(Integer pageNum, Integer pageSize, String searchWord, Boolean orderRecommend);

    List<AlbumDto> getAlbumList(String searchWord);

    List<AlbumDto> getAlbumSelectionList(String searchWord);

    AlbumDto getAlbumById(String id);


    AlbumDto createAlbum(AlbumCreateRequest albumCreateRequest);

    AlbumDto updateAlbumById(String id, AlbumUpdateRequest albumUpdateRequest);

    void deleteAlbumByID(String id);

    void changeAlbumStateToPublic(String id);

    void changeAlbumStateToClosed(String id);

    void changeAlbumStateToWaited(String id);
}
