package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    IPage<Tag> getPage(Page<Tag> page , @Param("name") String name);

    List<Tag> getTagList(@Param("name") String name);

    List<Tag> getTagSelectionList(@Param("name") String name);


    Tag selectTagById(@Param("id") String id);


    int batchInsertTagMusic(@Param("tag") Tag entity, @Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteTagMusicById(@Param("tag")Tag entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllTagMusicById(@Param("tag")Tag entity);



    int batchInsertTagPlayList(@Param("tag") Tag entity, @Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteTagPlayListById(@Param("tag")Tag entity,@Param("needDeleteIdList") List<String> needDeleteIdList);

    int deleteAllTagPlayListById(@Param("tag")Tag entity);


    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Tag tag);
}
