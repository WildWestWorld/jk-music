package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.service.ArtistService;
import com.wildwestworld.jkmusic.service.PlayListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Resource
    ArtistService artistService;


    @GetMapping("/page")
    public IPage<Artist> getPlayListPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "") String searchWord)
    {
        IPage<Artist> artistPage = artistService.getArtistPage(pageNum, pageSize, searchWord);

        return artistPage;

    }
}
