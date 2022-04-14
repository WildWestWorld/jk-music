package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AlbumMapper extends BaseMapper<Album> {
    List<Album> getAlbumList(@Param("name") String name);

    IPage<Album> getPage(Page<Album> page , @Param("name") String name, @Param("orderRecommend") Boolean orderRecommend);


    Album selectAlbumById(@Param("id") String id);


    int batchInsertAlbumMusic(@Param("album") Album entity, @Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteById(@Param("album")Album entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllAlbumMusicById(@Param("album")Album entity);

    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Album Album);
}
