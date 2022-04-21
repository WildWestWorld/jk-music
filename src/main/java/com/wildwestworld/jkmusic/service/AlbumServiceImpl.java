package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.AlbumState;
import com.wildwestworld.jkmusic.emuns.ArtistState;
import com.wildwestworld.jkmusic.emuns.PlayListState;
import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.AlbumMapper;
import com.wildwestworld.jkmusic.repository.AlbumRepository;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService{

    @Resource
    AlbumRepository albumRepository;

    @Resource
    AlbumMapper albumMapper;

    @Override
    @Transactional
    public AlbumDto createAlbum(AlbumCreateRequest albumCreateRequest) {
        //先给他转化成Entity
        Album albumEntity = albumRepository.createAlbumEntity(albumCreateRequest);
        System.out.println(albumEntity);
        //放入枚举类型的state
        AlbumState albumState = AlbumState.valueOf("待上架");
        albumEntity.setAlbumState(albumState);
        //将实体类插入数据
        albumMapper.insert(albumEntity);
        if (CollUtil.isNotEmpty(albumCreateRequest.getMusicIdList())) {
            albumMapper.batchInsertAlbumMusic(albumEntity, albumCreateRequest.getMusicIdList());
        }

        if (CollUtil.isNotEmpty(albumCreateRequest.getAlbumArtistIdList())) {
            albumMapper.batchInsertAlbumArtist(albumEntity, albumCreateRequest.getAlbumArtistIdList());
        }


        //然后转化为Dto
        AlbumDto albumDto = albumRepository.albumToDto(albumEntity);
        return albumDto;
    }

        @Override
    public List<AlbumDto> getAlbumList(String searchWord) {

            List<Album> albumList = albumMapper.getAlbumList(searchWord);

            List<AlbumDto> albumDtoList = albumList.stream().map(albumRepository::albumToDto).collect(Collectors.toList());
            return albumDtoList;
    }


    @Override
    @Transactional
    public AlbumDto updateAlbumById(String id, AlbumUpdateRequest albumUpdateRequest) {
        //后续需要进行修改
        Album album = albumMapper.selectAlbumById(id);
        if (album == null) {
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Album_NOT_FOUND);
        }

        //如果artistUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(albumUpdateRequest.getName())) {
            //设置名字
            album.setName(albumUpdateRequest.getName());
        }

        //如果artistUpdateRequest的ArtistState不是空的
        if (StrUtil.isNotEmpty(albumUpdateRequest.getAlbumState())) {
            //设置性别
            //获取文字形式的ArtistState
            String playlistState = albumUpdateRequest.getAlbumState();
            //利用文字形式的ArtistState和valueOf获取枚举形式的ArtistState
            AlbumState albumState = AlbumState.valueOf(playlistState);

            album.setAlbumState(albumState);
        }

        if (StrUtil.isNotEmpty(albumUpdateRequest.getDescription())) {
            album.setDescription(albumUpdateRequest.getDescription());
        }



        //如果albumUpdateRequest的RecommendFactor不是空的
        if (albumUpdateRequest.getRecommendFactor() != null) {
            album.setRecommendFactor(albumUpdateRequest.getRecommendFactor());
        }

        //如果albumUpdateRequest的Recommended不是空的

        if (albumUpdateRequest.getRecommended()  != null) {
            album.setRecommended(albumUpdateRequest.getRecommended());
        }

        if (albumUpdateRequest.getPhotoId()  != null & StrUtil.isNotEmpty(albumUpdateRequest.getPhotoId())) {
            album.setPhotoId(albumUpdateRequest.getPhotoId());
        }

//更新专辑与音乐的关系
        if (albumUpdateRequest.getMusicIdList() != null  ) {
            if (CollUtil.isNotEmpty(albumUpdateRequest.getMusicIdList())) {
                List<String> originIdList;
                if (album.getMusicList() != null & CollUtil.isNotEmpty(album.getMusicList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = album.getMusicList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(albumUpdateRequest.getMusicIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, albumUpdateRequest.getMusicIdList());


                if (needDeleteIdList.size() != 0) {
                    albumMapper.batchDeleteById(album, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    albumMapper.batchInsertAlbumMusic(album, needInsertIdList);
                }
            }else {

                if (album.getMusicList()  != null & CollUtil.isNotEmpty(album.getMusicList())) {
                    albumMapper.deleteAllAlbumMusicById(album);
                }
            }
        }



//更新专辑与艺人的关系
        if (albumUpdateRequest.getAlbumArtistIdList() != null ) {
            if(CollUtil.isNotEmpty(albumUpdateRequest.getAlbumArtistIdList())) {
                List<String> originIdList;
                if (album.getAlbumArtistList() != null & CollUtil.isNotEmpty(album.getAlbumArtistList())) {
                    //方案1:
                    //根据前端传过来的给的musicList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = album.getAlbumArtistList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(albumUpdateRequest.getAlbumArtistIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, albumUpdateRequest.getAlbumArtistIdList());

                if (needDeleteIdList.size() != 0) {
                    albumMapper.batchDeleteAlbumArtistById(album, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    albumMapper.batchInsertAlbumArtist(album, needInsertIdList);
                }
            }else{

                if (album.getAlbumArtistList() !=null & CollUtil.isNotEmpty(album.getAlbumArtistList())){
                    albumMapper.deleteAllAlbumArtistById(album);
                }

            }
        }

        //更新user
        albumMapper.updateById(album);


        //再次查询user
        //后续需要修改
        Album albumAfterUpdate = albumMapper.selectAlbumById(id);

        AlbumDto albumDto = albumRepository.albumToDto(albumAfterUpdate);
        return albumDto;
    }

    @Override
    @Transactional
    public void deleteAlbumByID(String id) {
        Album album = albumMapper.selectAlbumById(id);



        if (album.getMusicList() !=null & !CollUtil.isEmpty(album.getMusicList()) ) {
            albumMapper.deleteAllAlbumMusicById(album);
        }

        if (album.getMusicList() !=null & !CollUtil.isEmpty(album.getAlbumArtistList()) ) {
            albumMapper.deleteAllAlbumArtistById(album);
        }


        albumMapper.deleteById(id);
    }

    @Override
    public void changeAlbumStateToPublic(String id) {
        Album album = albumMapper.selectById(id);
        if(album == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Album_NOT_FOUND);
        }
        AlbumState musicState = AlbumState.valueOf("已上架");

        album.setAlbumState(musicState);

        albumMapper.updateById(album);
    }

    @Override
    public void changeAlbumStateToClosed(String id) {
        Album album = albumMapper.selectById(id);
        if(album == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Album_NOT_FOUND);
        }
        AlbumState musicState = AlbumState.valueOf("已下架");

        album.setAlbumState(musicState);

        albumMapper.updateById(album);
    }

    @Override
    public void changeAlbumStateToWaited(String id) {

        Album album = albumMapper.selectById(id);
        if(album == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Album_NOT_FOUND);
        }
        AlbumState musicState = AlbumState.valueOf("待上架");

        album.setAlbumState(musicState);

        albumMapper.updateById(album);
    }

    @Override
    public IPage<AlbumDto> getAlbumPage(Integer pageNum, Integer pageSize, String searchWord, Boolean orderRecommend) {
        IPage<Album> albumPage = albumMapper.getPage(new Page<>(pageNum, pageSize), searchWord,orderRecommend);

        List<Album> album = albumPage.getRecords();

        List<AlbumDto> albumDtoList = album.stream().map(albumRepository::albumToDto).collect(Collectors.toList());


        IPage<AlbumDto> albumDtoPage = new Page<>(pageNum, pageSize);
        albumDtoPage.setRecords(albumDtoList);

        albumDtoPage.setCurrent(albumPage.getCurrent());
        albumDtoPage.setTotal(albumPage.getTotal());
        albumDtoPage.setSize(albumDtoPage.getSize());

        return albumDtoPage;
    }


}
