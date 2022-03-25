package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArtistMapper extends BaseMapper<Artist> {
    IPage<Artist> getPage(Page<Artist> page , @Param("name") String name);

    List<Artist> getArtistList(@Param("name") String name);
}
