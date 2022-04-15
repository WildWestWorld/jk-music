package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArtistMapper extends BaseMapper<Artist> {
    IPage<Artist> getPage(Page<Artist> page , @Param("name") String name,@Param("orderRecommend") Boolean orderRecommend);

    List<Artist> getArtistList(@Param("name") String name);

    Artist selectArtistById(@Param("id") String id);

    int batchInsertArtistMusic(@Param("artist") Artist entity, @Param("needInsertIdList") List<String> needInsertIdList);


    int batchInsertArtistAlbum(@Param("artist") Artist entity, @Param("needInsertIdList") List<String> needInsertIdList);


    int batchDeleteByIdFromArtistMusic(@Param("artist")Artist entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int batchDeleteArtistAlbumById(@Param("artist")Artist entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllArtistMusicById(@Param("artist")Artist entity);

    int deleteAllArtistAlbumById(@Param("artist")Artist entity);


    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Artist entity);
}
