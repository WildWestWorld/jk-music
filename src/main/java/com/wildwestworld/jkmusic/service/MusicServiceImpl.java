package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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


//        musicMapper.insertArtistMusic(musicEntity);

        if (CollUtil.isNotEmpty(musicCreateRequest.getArtistIdList())) {
            musicMapper.batchInsertMusicArtist(musicEntity, musicCreateRequest.getArtistIdList());
        }

        if (CollUtil.isNotEmpty(musicCreateRequest.getAlbumIdList())) {
            musicMapper.batchInsertMusicAlbum(musicEntity, musicCreateRequest.getAlbumIdList());
        }


        if (CollUtil.isNotEmpty(musicCreateRequest.getTagIdList())) {
            musicMapper.batchInsertMusicTag(musicEntity, musicCreateRequest.getTagIdList());
        }


        //然后转化为Dto
        MusicDto musicDto = musicRepository.musicToDto(musicEntity);
        return musicDto;
    }

    //更新Music
    @Override
    @Transactional
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


        //如果userUpdateRequest的FileId不是空的
        System.out.println(StrUtil.isNotEmpty(musicUpdateRequest.getPhotoId()));
        if (StrUtil.isNotEmpty(musicUpdateRequest.getPhotoId())) {
            //设置昵称
            music.setPhotoId(musicUpdateRequest.getPhotoId());
        }else{
            music.setPhotoId(musicUpdateRequest.getPhotoId());

        }
//更新音乐与歌手的关系
        if (musicUpdateRequest.getArtistIdList() != null ) {
            if (CollUtil.isNotEmpty(musicUpdateRequest.getArtistIdList())) {
                List<String> originIdList;
                if (music.getArtistList() != null & CollUtil.isNotEmpty(music.getArtistList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = music.getArtistList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(musicUpdateRequest.getArtistIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, musicUpdateRequest.getArtistIdList());

                if (needDeleteIdList.size() != 0) {
                    musicMapper.batchDeleteMusicArtistById(music, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    musicMapper.batchInsertMusicArtist(music, needInsertIdList);
                }


            }else{
                if (music.getArtistList() != null & CollUtil.isNotEmpty(music.getArtistList())) {
                    musicMapper.deleteAllMusicArtistById(music);
                }
            }
        }


//更新音乐与专辑的关系
        if (musicUpdateRequest.getAlbumIdList() != null ) {
            if (CollUtil.isNotEmpty(musicUpdateRequest.getAlbumIdList())) {
                List<String> originIdList;
                if (music.getAlbumList() != null & CollUtil.isNotEmpty(music.getAlbumList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = music.getAlbumList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(musicUpdateRequest.getAlbumIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, musicUpdateRequest.getAlbumIdList());

                if (needDeleteIdList.size() != 0) {
                    musicMapper.batchDeleteMusicAlbumById(music, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    musicMapper.batchInsertMusicAlbum(music, needInsertIdList);
                }


            }else{
                if (music.getAlbumList() != null & CollUtil.isNotEmpty(music.getAlbumList())) {
                    musicMapper.deleteAllMusicAlbumById(music);
                }
            }
        }

//更新音乐与标签的关系
        if (musicUpdateRequest.getTagIdList() != null ) {
            if (CollUtil.isNotEmpty(musicUpdateRequest.getTagIdList())) {
                List<String> originIdList;
                if (music.getTagList() != null & CollUtil.isNotEmpty(music.getTagList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = music.getTagList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(musicUpdateRequest.getTagIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, musicUpdateRequest.getTagIdList());

                if (needDeleteIdList.size() != 0) {
                    musicMapper.batchDeleteMusicTagById(music, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    musicMapper.batchInsertMusicTag(music, needInsertIdList);
                }


            }else{
                if (music.getTagList() != null & CollUtil.isNotEmpty(music.getTagList())) {
                    musicMapper.deleteAllMusicTagById(music);
                }
            }
        }

        //更新user
        musicMapper.updateById(music);
//        if (musicUpdateRequest.getArtistIdList() !=null) {
//            if (music.getArtistList().size() !=0){
//                musicMapper.deleteAllById(music);
//            }
//
//            Music musicEntity = musicRepository.UpdateMusicEntity(musicUpdateRequest);
//            musicEntity.setId(id);
//
//            musicMapper.insertArtistMusic(musicEntity);
//        }
        //再次查询user
        Music updateMusic = musicMapper.selectMusicById(id);

        MusicDto musicDto = musicRepository.musicToDto(updateMusic);
        return musicDto;
    }

    //删除Music
    @Override
    @Transactional
    public void deleteMusicByID(String id) {

        Music music = musicMapper.selectMusicById(id);
        if (music.getArtistList() !=null & !CollUtil.isEmpty(music.getArtistList()) ) {
            musicMapper.deleteAllMusicArtistById(music);
        }

        if (music.getAlbumList()!=null & !CollUtil.isEmpty(music.getAlbumList()) ) {
            musicMapper.deleteAllMusicAlbumById(music);
        }

        if (music.getTagList()!=null & !CollUtil.isEmpty(music.getTagList()) ) {
            musicMapper.deleteAllMusicTagById(music);
        }


        musicMapper.deleteById(music);
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
