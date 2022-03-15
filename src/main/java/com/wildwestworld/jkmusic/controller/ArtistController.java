package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.repository.ArtistRepository;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.service.ArtistService;
import com.wildwestworld.jkmusic.service.PlayListService;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.ArtistVo;
import com.wildwestworld.jkmusic.transport.vo.MusicVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Resource
    ArtistService artistService;
    @Resource
    private ArtistRepository artistRepository;

    @PostMapping
    public ArtistVo createArtist(@Validated @RequestBody ArtistCreateRequest artistCreateRequest){
        ArtistDto artistDto = artistService.createArtist(artistCreateRequest);

        ArtistVo artistVo = artistRepository.artistToVo(artistDto);

        return artistVo;
    }

    @PutMapping("/{id}")
    public ArtistVo updateArtist(@Validated @PathVariable String id, @RequestBody ArtistUpdateRequest artistUpdateRequest){
        ArtistDto artistDto = artistService.updateArtistById(id, artistUpdateRequest);
        ArtistVo artistVo = artistRepository.artistToVo(artistDto);
        return artistVo;
    }


    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    Result<?> deleteMusicByID(@PathVariable String id){
        artistService.deleteArtistByID(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/public")
    Result<?> changeArtistStateToPublic(@PathVariable String id){
        artistService.changeArtistStateToPublic(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/closed")
    Result<?> changeArtistStateToClosed(@PathVariable String id){
        artistService.changeArtistStateToClosed(id);
        return Result.success();
    }

    //根据ID修改music的state改为已上架状态
    @PostMapping("/{id}/waited")
    Result<?> changeArtistStateToWaited(@PathVariable String id){
        artistService.changeArtistStateToWaited(id);
        return Result.success();
    }

    @GetMapping("/page")
    public IPage<Artist> getPlayListPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "") String searchWord)
    {
        IPage<Artist> artistPage = artistService.getArtistPage(pageNum, pageSize, searchWord);

        return artistPage;
    }
}
