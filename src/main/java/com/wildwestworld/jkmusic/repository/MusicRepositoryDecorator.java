//package com.wildwestworld.jkmusic.repository;
//
//import com.wildwestworld.jkmusic.entity.*;
//import com.wildwestworld.jkmusic.service.MusicService;
//import com.wildwestworld.jkmusic.service.StorageService;
//import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
//import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
//import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
//import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
//import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
//import com.wildwestworld.jkmusic.transport.vo.AlbumVo;
//import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
//import com.wildwestworld.jkmusic.transport.vo.MusicVo;
//import com.wildwestworld.jkmusic.transport.vo.TagVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public abstract class MusicRepositoryDecorator implements MusicRepository {
//    @Autowired
//    @Qualifier("delegate")
//    private MusicRepository delegate;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//
//    @Autowired
//    private AlbumRepository albumRepository;
//
//
//    @Override
//    public MusicDto musicToDto(Music music) {
//        MusicDto musicDto = delegate.musicToDto(music);
//        if(musicDto == null){
//            return null;
//        }
//        List<Tag> tagList = music.getTagList();
//        if (tagList!=null) {
//            List<TagDto> tagDtoList = tagList.stream().map(tagRepository::tagToDto).collect(Collectors.toList());
//            musicDto.setTagDtoList(tagDtoList);
//        }
//
////        List<Album> albumList = music.getAlbumList();
////
////        if (albumList!=null) {
////            List<AlbumDto> albumDtoList = albumList.stream().map(albumRepository::albumToDto).collect(Collectors.toList());
////            musicDto.setAlbumDtoList(albumDtoList);
////        }
//        return musicDto;
//    }
//
//    @Override
//    public MusicVo musicToVo(MusicDto musicDto) {
//        MusicVo musicVo = delegate.musicToVo(musicDto);
//        if(musicVo == null){
//            return null;
//        }
//        List<TagDto> tagDtoList = musicDto.getTagDtoList();
//        if (tagDtoList!=null) {
//            List<TagVo> tagVoList = tagDtoList.stream().map(tagRepository::tagToVo).collect(Collectors.toList());
//            musicVo.setTagVoList(tagVoList);
//        }
//
////        List<AlbumDto> albumDtoList = musicDto.getAlbumDtoList();
////
////        if (albumDtoList!=null) {
////            List<AlbumVo> albumVoList = albumDtoList.stream().map(albumRepository::albumToVo).collect(Collectors.toList());
////            musicVo.setAlbumVoList(albumVoList);
////        }
//        return musicVo;
//    }
//}
