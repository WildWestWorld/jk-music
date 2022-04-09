package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.PlayList;
import org.apache.ibatis.annotations.Param;

public interface PlayListMapper extends BaseMapper<PlayList> {

    IPage<PlayList> getPage(Page<PlayList> page , @Param("name") String name,@Param("orderRecommend") Boolean orderRecommend);

}
