package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.repository.TagRepository;
import com.wildwestworld.jkmusic.service.MusicService;
import com.wildwestworld.jkmusic.service.TagService;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import com.wildwestworld.jkmusic.transport.vo.TagVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @Resource
    private TagRepository tagRepository;

    @GetMapping
    public List<TagVo> getTagList(@RequestParam(defaultValue = "")String searchWord){

        List<TagDto> tagList = tagService.getTagList(searchWord);
        List<TagVo> tagVoList = tagList.stream().map(tagRepository::tagToVo).collect(Collectors.toList());
        return tagVoList;
    }

    @PostMapping
    public TagVo createTag(@Validated @RequestBody TagCreateRequest tagCreateRequest){
        TagDto tagDto = tagService.createTag(tagCreateRequest);

        TagVo tagVo = tagRepository.tagToVo(tagDto);

        return tagVo;
    }

    @PutMapping("/{id}")
    public TagVo updateTag(@Validated @PathVariable String id, @RequestBody TagUpdateRequest tagUpdateRequest){
        TagDto tagDto = tagService.updateTagById(id, tagUpdateRequest);
        TagVo tagVo = tagRepository.tagToVo(tagDto);
        return tagVo;
    }

    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    Result<?> deleteTagByID(@PathVariable String id){
        tagService.deleteTagByID(id);
        return Result.success();
    }


    //分页
    @GetMapping("/pages")
    public IPage<TagVo> getTagPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "")String searchWord){
        IPage<TagDto> tagDtoPage = tagService.getTagPage(pageNum, pageSize, searchWord);

        List<TagDto> tagDtoList = tagDtoPage.getRecords();

        List<TagVo> tagVoList = tagDtoList.stream().map(tagRepository::tagToVo).collect(Collectors.toList());


        IPage<TagVo> tagVoPage =new Page<>(pageNum,pageSize);
        tagVoPage.setRecords(tagVoList);
        tagVoPage.setCurrent(tagDtoPage.getCurrent());
        tagVoPage.setTotal(tagDtoPage.getTotal());
        tagVoPage.setSize(tagDtoPage.getSize());

        return  tagVoPage;
    }
}
