package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.repository.ArtistRepository;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.service.ArtistService;
import com.wildwestworld.jkmusic.service.PlayListService;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;

import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Resource
    ArtistService artistService;
    @Resource
    ArtistRepository artistRepository;

    @GetMapping
    public List<ArtistVo> getArtistList(@RequestParam(defaultValue = "")String searchWord){

        List<ArtistDto> artistList = artistService.getArtistList(searchWord);
        List<ArtistVo> artistVoList = artistList.stream().map(artistRepository::artistToVo).collect(Collectors.toList());
        return artistVoList;
    }

    @GetMapping("/selection")
    public List<ArtistVo> getArtistSelectionList(@RequestParam(defaultValue = "")String searchWord){

        List<ArtistDto> artistList = artistService.getArtistSelectionList(searchWord);
        List<ArtistVo> artistVoList = artistList.stream().map(artistRepository::artistToVo).collect(Collectors.toList());
        return artistVoList;
    }

    @PostMapping
    public ArtistVo createArtist(@Validated @RequestBody ArtistCreateRequest artistCreateRequest){
        ArtistDto artistDto = artistService.createArtist(artistCreateRequest);

        ArtistVo artistVo = artistRepository.artistToVo(artistDto);

        return artistVo;
    }

    @PutMapping("/{id}")
    public ArtistVo updateArtist( @PathVariable String id, @Validated @RequestBody ArtistUpdateRequest artistUpdateRequest){
        ArtistDto artistDto = artistService.updateArtistById(id, artistUpdateRequest);
        ArtistVo artistVo = artistRepository.artistToVo(artistDto);
        return artistVo;
    }


    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    public Result<?> deleteMusicByID(@PathVariable String id){
        artistService.deleteArtistByID(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/public")
    public Result<?> changeArtistStateToPublic(@PathVariable String id){
        artistService.changeArtistStateToPublic(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/closed")
    public Result<?> changeArtistStateToClosed(@PathVariable String id){
        artistService.changeArtistStateToClosed(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/waited")
    public Result<?> changeArtistStateToWaited(@PathVariable String id){
        artistService.changeArtistStateToWaited(id);
        return Result.success();
    }

    //根据ID修改artist的recommend为真
    @PostMapping("/{id}/recommend")
    public ArtistVo recommend(@PathVariable String id,@Validated @RequestBody ArtistRecommendRequest artistRecommendRequest){
        ArtistDto artistDto = artistService.changeToRecommend(id, artistRecommendRequest);
        ArtistVo artistVo = artistRepository.artistToVo(artistDto);

        return artistVo;
    }

    //根据ID修改artist的recommend为真
    @PostMapping("/{id}/cancel_recommend")
    public ArtistVo cancelRecommend(@PathVariable String id){
        ArtistDto artistDto = artistService.cancelRecommend(id);
        ArtistVo artistVo = artistRepository.artistToVo(artistDto);

        return artistVo;
    }

    @GetMapping("/page")
    public IPage<ArtistVo> getPlayListPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "") String searchWord,
                                           @RequestParam(defaultValue = "false") Boolean orderRecommend)
    {
        IPage<ArtistDto> artistDtoPage = artistService.getArtistPage(pageNum, pageSize, searchWord,orderRecommend);

        List<ArtistDto> artistDtoList = artistDtoPage.getRecords();

        List<ArtistVo> artistVoList = artistDtoList.stream().map(artistRepository::artistToVo).collect(Collectors.toList());


        IPage<ArtistVo> artistVoPage =new Page<>(pageNum,pageSize);
        artistVoPage.setRecords(artistVoList);
        artistVoPage.setCurrent(artistDtoPage.getCurrent());
        artistVoPage.setTotal(artistDtoPage.getTotal());
        artistVoPage.setSize(artistDtoPage.getSize());



        return artistVoPage;
    }
}
