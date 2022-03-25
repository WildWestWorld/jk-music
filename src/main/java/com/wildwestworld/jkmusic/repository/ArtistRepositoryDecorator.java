package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.File;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.service.MusicService;
import com.wildwestworld.jkmusic.service.StorageService;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ArtistRepositoryDecorator implements ArtistRepository {
    @Autowired
    @Qualifier("delegate")
    private ArtistRepository delegate;

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public ArtistDto artistToDto(Artist artist) {
        ArtistDto artistDto = delegate.artistToDto(artist);
        if(artistDto == null){
            return null;
        }
        List<Music> musicList = artist.getMusicList();
        List<MusicDto> musicDtoList = musicList.stream().map(musicRepository::musicToDto).collect(Collectors.toList());
        artistDto.setMusicDtoList(musicDtoList);
        return artistDto;
    }

    @Override
    public ArtistVo artistToVo(ArtistDto artistDto) {
        ArtistVo artistVo = delegate.artistToVo(artistDto);
        if(artistVo == null){
            return null;
        }
        List<MusicDto> musicDtoList = artistDto.getMusicDtoList();
        List<MusicVo> musicVoList = musicDtoList.stream().map(musicRepository::musicToVo).collect(Collectors.toList());
        artistVo.setMusicVoList(musicVoList);
        return artistVo;
    }
}