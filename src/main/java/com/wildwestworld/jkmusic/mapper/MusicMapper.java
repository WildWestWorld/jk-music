package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {

    IPage<Music> getPage(Page<Music> page , @Param("name") String name);

    List<Music> getMusicList(@Param("name") String name);

    Music selectMusicById(@Param("id") String id);

    int insertArtistMusic(Music entity);

    int deleteAllById(Music entity);
    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Music entity);
}
