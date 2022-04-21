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
    public TagDto createTag(TagCreateRequest tagCreateRequest) {
        //先给他转化成Entity
        Tag tagEntity = tagRepository.createTagEntity(tagCreateRequest);
        System.out.println(tagEntity);

        //将实体类插入数据
        tagMapper.insert(tagEntity);



        System.out.println(tagEntity.getId());


//        tagMapper.insertArtistTag(tagEntity);

//        if (CollUtil.isNotEmpty(tagCreateRequest.getArtistIdList())) {
//            tagMapper.batchInsertTagArtist(tagEntity, tagCreateRequest.getArtistIdList());
//        }
//
//        if (CollUtil.isNotEmpty(tagCreateRequest.getAlbumIdList())) {
//            tagMapper.batchInsertTagAlbum(tagEntity, tagCreateRequest.getAlbumIdList());
//        }

        //然后转化为Dto
        TagDto tagDto = tagRepository.tagToDto(tagEntity);
        return tagDto;
    }

    @Override
    public TagDto updateTagById(String id, TagUpdateRequest tagUpdateRequest) {
        Tag tag = tagMapper.selectById(id);
        if(tag == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Tag_NOT_FOUND);
        }

        //如果musicUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(tagUpdateRequest.getName())){
            //设置名字
            tag.setName(tagUpdateRequest.getName());
        }


        //更新user
        tagMapper.updateById(tag);

        //再次查询user
        Tag updateTag = tagMapper.selectById(id);

        TagDto tagDto = tagRepository.tagToDto(updateTag);
        return tagDto;
    }

    @Override
    public void deleteTagByID(String id) {
        Tag tag = tagMapper.selectById(id);


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
