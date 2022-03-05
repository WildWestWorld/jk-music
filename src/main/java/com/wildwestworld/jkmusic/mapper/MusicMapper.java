package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {

    IPage<Music> getPage(Page<Music> page , @Param("name") String name);

}
