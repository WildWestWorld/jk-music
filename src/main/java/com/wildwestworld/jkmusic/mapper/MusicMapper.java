package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
}
