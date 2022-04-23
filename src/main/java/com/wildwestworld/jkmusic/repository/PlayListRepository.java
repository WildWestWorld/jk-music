package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserCreateByRequest;
import com.wildwestworld.jkmusic.transport.dto.User.UserDto;
import com.wildwestworld.jkmusic.transport.vo.PlayListVo;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",uses = {FileRepository.class,MusicRepository.class})
public interface PlayListRepository {
    //将user类转化为UserDto
    @Mapping(target="tagList",source="tagList")

    PlayListDto playListToDto(PlayList playList);

    //将dto转化为Vo
    @Mapping(target="tagList",source="tagList")

    PlayListVo playListToVo(PlayListDto playListDto);

    PlayList createPlayListEntity (PlayListCreateRequest playListCreateRequest);


//    @Mapping(target="musicList",source="musicIdList")
//    PlayList UpdatePlayListEntity (PlayListUpdateRequest playListUpdateRequest);
//
//    default List<Music> mapMusicList(List<String> musicIdList){
//        List<Music> musicList = new ArrayList();
//        for (String id : musicIdList) {
//            Music music = new Music();
//            music.setId(id);
//            musicList.add(music);
//        }
//        return  musicList;
//    }

}
