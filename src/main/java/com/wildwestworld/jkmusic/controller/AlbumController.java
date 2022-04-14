package com.wildwestworld.jkmusic.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.repository.AlbumRepository;
import com.wildwestworld.jkmusic.repository.AlbumRepository;
import com.wildwestworld.jkmusic.service.AlbumService;
import com.wildwestworld.jkmusic.service.ArtistService;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumDto;
import com.wildwestworld.jkmusic.transport.dto.Album.AlbumUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.vo.AlbumVo;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.PlayListVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Resource
    AlbumService albumService;
    @Resource
    AlbumRepository albumRepository;

    @GetMapping
    public List<AlbumVo> getAlbumList(@RequestParam(defaultValue = "")String searchWord){

        List<AlbumDto> albumList = albumService.getAlbumList(searchWord);
        List<AlbumVo> albumVoList = albumList.stream().map(albumRepository::albumToVo).collect(Collectors.toList());
        return albumVoList;
    }

    @PostMapping
    public AlbumVo createAlbum(@Validated @RequestBody AlbumCreateRequest albumCreateRequest){
        AlbumDto albumDto = albumService.createAlbum(albumCreateRequest);

        AlbumVo albumVo = albumRepository.albumToVo(albumDto);

        return albumVo;
    }

    @PutMapping("/{id}")
    public AlbumVo updateAlbum( @PathVariable String id, @Validated @RequestBody AlbumUpdateRequest albumUpdateRequest){
        AlbumDto albumDto = albumService.updateAlbumById(id, albumUpdateRequest);
        AlbumVo albumVo = albumRepository.albumToVo(albumDto);
        return albumVo;
    }

    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    public Result<?> deleteAlbumByID(@PathVariable String id){
        albumService.deleteAlbumByID(id);
        return Result.success();
    }


    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/public")
    Result<?> changeAlbumStateToPublic(@PathVariable String id){
        albumService.changeAlbumStateToPublic(id);
        return Result.success();
    }

    //根据ID修改music的state改为已下架状态
    @PostMapping("/{id}/closed")
    Result<?> changeAlbumStateToClosed(@PathVariable String id){
        albumService.changeAlbumStateToClosed(id);
        return Result.success();
    }

    //根据ID修改music的state改为待上架状态
    @PostMapping("/{id}/waited")
    Result<?> changeAlbumStateToWaited(@PathVariable String id){
        albumService.changeAlbumStateToWaited(id);
        return Result.success();
    }



    @GetMapping("/page")
    public IPage<AlbumVo> getAlbumPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "") String searchWord,
                                             @RequestParam(defaultValue = "false") Boolean orderRecommend){
        IPage<AlbumDto> albumDtoPage = albumService.getAlbumPage(pageNum, pageSize, searchWord,orderRecommend);


        List<AlbumDto> albumDtoList = albumDtoPage.getRecords();

        List<AlbumVo> albumVoList = albumDtoList.stream().map(albumRepository::albumToVo).collect(Collectors.toList());


        IPage<AlbumVo> albumVoPage =new Page<>(pageNum,pageSize);
        albumVoPage.setRecords(albumVoList);
        albumVoPage.setCurrent(albumDtoPage.getCurrent());
        albumVoPage.setTotal(albumDtoPage.getTotal());
        albumVoPage.setSize(albumDtoPage.getSize());

        return albumVoPage;

    }


}
