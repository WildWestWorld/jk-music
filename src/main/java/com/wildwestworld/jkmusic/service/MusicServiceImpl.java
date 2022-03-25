package com.wildwestworld.jkmusic.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.MusicMapper;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.transport.dto.File.FileDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService{
    @Resource
    private MusicMapper musicMapper;
    @Resource
    private MusicRepository musicRepository;

    @Resource
    private  StorageService storageService;
    //创建MUSIC
    @Override
    public MusicDto createMusic(MusicCreateRequest musicCreateRequest) {
        //先给他转化成Entity
        Music musicEntity = musicRepository.createMusicEntity(musicCreateRequest);
        System.out.println(musicEntity);
        //放入枚举类型的state
        MusicState state = MusicState.valueOf("待上架");
        musicEntity.setMusicState(state);
        //将实体类插入数据
        musicMapper.insert(musicEntity);

        System.out.println(musicEntity.getId());


        musicMapper.insertArtistMusic(musicEntity);
        //然后转化为Dto
        MusicDto musicDto = musicRepository.musicToDto(musicEntity);
        return musicDto;
    }

    //更新Music
    @Override
    public MusicDto updateMusicById(String id, MusicUpdateRequest musicUpdateRequest) {
        Music music = musicMapper.selectMusicById(id);
        if(music == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Music_NOT_FOUND);
        }

        //如果musicUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(musicUpdateRequest.getName())){
            //设置名字
            music.setName(musicUpdateRequest.getName());
        }

        //如果musicUpdateRequest的MusicState不是空的
        if (StrUtil.isNotEmpty(musicUpdateRequest.getMusicState())) {
            //设置性别
            //获取文字形式的MusicState
            String musicState = musicUpdateRequest.getMusicState();
            //利用文字形式的MusicState和valueOf获取枚举形式的MusicState
            MusicState State = MusicState.valueOf(musicState);

            music.setMusicState(State);
        }

        //如果userUpdateRequest的Nickname不是空的
        if (StrUtil.isNotEmpty(musicUpdateRequest.getDescription())) {
            //设置昵称
            music.setDescription(musicUpdateRequest.getDescription());
        }

        //如果userUpdateRequest的FileId不是空的
        if (StrUtil.isNotEmpty(musicUpdateRequest.getFileId())) {
            //设置昵称
            music.setFileId(musicUpdateRequest.getFileId());
        }

        //更新user
        musicMapper.updateById(music);
        if (musicUpdateRequest.getArtistIdList() !=null) {
            if (music.getArtistList().size() !=0){
                musicMapper.deleteAllById(music);
            }

            Music musicEntity = musicRepository.UpdateMusicEntity(musicUpdateRequest);
            musicEntity.setId(id);

            musicMapper.insertArtistMusic(musicEntity);
        }
        //再次查询user
        Music updateMusic = musicMapper.selectMusicById(id);

        MusicDto musicDto = musicRepository.musicToDto(updateMusic);
        return musicDto;
    }

    //删除Music
    @Override
    public void deleteMusicByID(String id) {
        musicMapper.deleteById(id);

    }

    //获取全部music
    @Override
    public List<MusicDto> getMusicList(String searchWord) {
        LambdaQueryWrapper<Music> wrapper = Wrappers.<Music>lambdaQuery();
        wrapper.like(Music::getName,searchWord);

        List<Music> musicList = musicMapper.getMusicList(searchWord);

        List<MusicDto> musicDtoList = musicList.stream().map(musicRepository::musicToDto).collect(Collectors.toList());
        return musicDtoList;
    }

    @Override
    public void changeMusicStateToPublic(String id) {
        Music music = musicMapper.selectById(id);
        if(music == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Music_NOT_FOUND);
        }
        MusicState musicState = MusicState.valueOf("已上架");

        music.setMusicState(musicState);

        musicMapper.updateById(music);
    }

    @Override
    public void changeMusicStateToClosed(String id) {
        Music music = musicMapper.selectById(id);
        if(music == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Music_NOT_FOUND);
        }
        MusicState musicState = MusicState.valueOf("已下架");

        music.setMusicState(musicState);

        musicMapper.updateById(music);
    }

    @Override
    public void changeMusicStateToWaited(String id) {
        Music music = musicMapper.selectById(id);
        if(music == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Music_NOT_FOUND);
        }
        MusicState musicState = MusicState.valueOf("待上架");

        music.setMusicState(musicState);

        musicMapper.updateById(music);
    }


    @Override
    public IPage<MusicDto> getMusicPage(Integer pageNum, Integer pageSize, String searchWord) {
        LambdaQueryWrapper<Music> wrapper = Wrappers.<Music>lambdaQuery();
        if (StrUtil.isNotBlank(searchWord)){
            wrapper.eq(Music::getName,searchWord);
        }



        IPage<Music> musicPage = musicMapper.getPage(new Page<>(pageNum, pageSize), searchWord);


        List<Music> musicList = musicPage.getRecords();
        for (Music music : musicList) {
            List<Artist> artistList = music.getArtistList();
        }
        List<MusicDto> musicDtoList = musicList.stream().map(musicRepository::musicToDto).collect(Collectors.toList());
//        for (MusicDto musicDto : musicDtoList) {
//            FileDto fileDto = musicDto.getFile();
//            if (fileDto !=null){
//                String hashKey = fileDto.getHashKey();
//
//            }
//        }

        IPage<MusicDto> musicDtoPage = new Page<>(pageNum,pageSize);

        musicDtoPage.setRecords(musicDtoList);

        musicDtoPage.setCurrent(musicPage.getCurrent());
        musicDtoPage.setTotal(musicPage.getTotal());
        musicDtoPage.setSize(musicPage.getSize());

        return musicDtoPage;
    }
}
