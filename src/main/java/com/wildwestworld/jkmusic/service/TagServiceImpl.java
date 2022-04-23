package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.emuns.MusicState;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.TagMapper;
import com.wildwestworld.jkmusic.repository.TagRepository;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{
    @Resource
    TagMapper tagMapper;

    @Resource
    TagRepository tagRepository;

    @Override
    public List<TagDto> getTagList(String searchWord) {
        LambdaQueryWrapper<Tag> wrapper = Wrappers.<Tag>lambdaQuery();
        wrapper.like(Tag::getName,searchWord);

        List<Tag> tagList = tagMapper.getTagList(searchWord);

        List<TagDto> tagDtoList = tagList.stream().map(tagRepository::tagToDto).collect(Collectors.toList());
        return tagDtoList;
    }


    @Override
    @Transactional
    public TagDto createTag(TagCreateRequest tagCreateRequest) {
        //先给他转化成Entity
        Tag tagEntity = tagRepository.createTagEntity(tagCreateRequest);
        System.out.println(tagEntity);

        //将实体类插入数据
        tagMapper.insert(tagEntity);



        System.out.println(tagEntity.getId());


//        tagMapper.insertArtistTag(tagEntity);

        if (CollUtil.isNotEmpty(tagCreateRequest.getMusicIdList())) {
            tagMapper.batchInsertTagMusic(tagEntity, tagCreateRequest.getMusicIdList());
        }
//
        if (CollUtil.isNotEmpty(tagCreateRequest.getPlayListIdList())) {
            tagMapper.batchInsertTagPlayList(tagEntity, tagCreateRequest.getPlayListIdList());
        }

        //然后转化为Dto
        TagDto tagDto = tagRepository.tagToDto(tagEntity);
        return tagDto;
    }

    @Override
    @Transactional

    public TagDto updateTagById(String id, TagUpdateRequest tagUpdateRequest) {
        Tag tag = tagMapper.selectTagById(id);
        if(tag == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Tag_NOT_FOUND);
        }

        //如果musicUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(tagUpdateRequest.getName())){
            //设置名字
            tag.setName(tagUpdateRequest.getName());
        }



//更新音乐与歌手的关系
        if (tagUpdateRequest.getMusicIdList() != null ) {
            if (CollUtil.isNotEmpty(tagUpdateRequest.getMusicIdList())) {
                List<String> originIdList;
                if (tag.getMusicList() != null & CollUtil.isNotEmpty(tag.getMusicList())) {
                    //方案1:
                    //根据前端传过来的给的tagList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = tag.getMusicList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(tagUpdateRequest.getMusicIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, tagUpdateRequest.getMusicIdList());

                if (needDeleteIdList.size() != 0) {
                    tagMapper.batchDeleteTagMusicById(tag, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    tagMapper.batchInsertTagMusic(tag, needInsertIdList);
                }


            }else{
                if (tag.getMusicList() != null & CollUtil.isNotEmpty(tag.getMusicList())) {
                    tagMapper.deleteAllTagMusicById(tag);
                }
            }
        }


//更新音乐与歌单的关系
        if (tagUpdateRequest.getPlayListIdList() != null ) {
            if (CollUtil.isNotEmpty(tagUpdateRequest.getPlayListIdList())) {
                List<String> originIdList;
                if (tag.getPlayList() != null & CollUtil.isNotEmpty(tag.getPlayList())) {
                    //方案1:
                    //根据前端传过来的给的tagList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = tag.getPlayList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(tagUpdateRequest.getPlayListIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, tagUpdateRequest.getPlayListIdList());

                if (needDeleteIdList.size() != 0) {
                    tagMapper.batchDeleteTagPlayListById(tag, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    tagMapper.batchInsertTagPlayList(tag, needInsertIdList);
                }


            }else{
                if (tag.getPlayList() != null & CollUtil.isNotEmpty(tag.getPlayList())) {
                    tagMapper.deleteAllTagPlayListById(tag);
                }
            }
        }



        //更新user
        tagMapper.updateById(tag);

        //再次查询user
        Tag updateTag = tagMapper.selectTagById(id);

        TagDto tagDto = tagRepository.tagToDto(updateTag);
        return tagDto;
    }

    @Override
    @Transactional
    public void deleteTagByID(String id) {
        Tag tag = tagMapper.selectTagById(id);

        if (tag.getMusicList() !=null & !CollUtil.isEmpty(tag.getMusicList()) ) {
            tagMapper.deleteAllTagMusicById(tag);
        }

        if (tag.getPlayList() !=null & !CollUtil.isEmpty(tag.getPlayList()) ) {
            tagMapper.deleteAllTagPlayListById(tag);
        }
        tagMapper.deleteById(tag);
    }

    @Override
    public IPage<TagDto> getTagPage(Integer pageNum, Integer pageSize, String searchWord) {
        LambdaQueryWrapper<Tag> wrapper = Wrappers.<Tag>lambdaQuery();
        if (StrUtil.isNotBlank(searchWord)){
            wrapper.eq(Tag::getName,searchWord);
        }



        IPage<Tag> tagPage = tagMapper.getPage(new Page<>(pageNum, pageSize), searchWord);


        List<Tag> tagList = tagPage.getRecords();

        List<TagDto> tagDtoList = tagList.stream().map(tagRepository::tagToDto).collect(Collectors.toList());
//        for (TagDto tagDto : tagDtoList) {
//            FileDto fileDto = tagDto.getFile();
//            if (fileDto !=null){
//                String hashKey = fileDto.getHashKey();
//
//            }
//        }

        IPage<TagDto> tagDtoPage = new Page<>(pageNum,pageSize);

        tagDtoPage.setRecords(tagDtoList);

        tagDtoPage.setCurrent(tagPage.getCurrent());
        tagDtoPage.setTotal(tagPage.getTotal());
        tagDtoPage.setSize(tagPage.getSize());

        return tagDtoPage;
    }


}
