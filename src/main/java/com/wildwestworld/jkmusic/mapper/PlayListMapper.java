package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Album;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayListMapper extends BaseMapper<PlayList> {

    IPage<PlayList> getPage(Page<PlayList> page , @Param("name") String name,@Param("orderRecommend") Boolean orderRecommend);

    List<PlayList> getPlayList(@Param("name") String name);

    List<PlayList> getPlaySelectionList(@Param("name") String name);



    PlayList selectPlayListById(@Param("id") String id);


    int batchInsertPlayListMusic(@Param("playList")PlayList entity,@Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteById(@Param("playList")PlayList entity,@Param("needDeleteIdList") List<String> needDeleteIdList);


    int deleteAllPlayListMusicById(@Param("playList") PlayList entity);


    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PlayList entity);
}
