package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;

public interface ArtistService {
    IPage<Artist> getArtistPage(Integer pageNum, Integer pageSize, String searchWord);

    ArtistDto createArtist(ArtistCreateRequest artistCreateRequest);

    ArtistDto updateArtistById(String id, ArtistUpdateRequest artistUpdateRequest);

    void deleteArtistByID(String id);

    void changeArtistStateToPublic(String id);

    void changeArtistStateToClosed(String id);

    void changeArtistStateToWaited(String id);

}
