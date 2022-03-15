package com.wildwestworld.jkmusic.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.service.PlayListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/playlist")
public class PlayListController {
    @Resource
    PlayListService playListService;


    @GetMapping("/page")
    public IPage<PlayList> getPlayListPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String searchWord
                                    ){
        IPage<PlayList> playListPage = playListService.getPlayListPage(pageNum, pageSize, searchWord);

        return playListPage;

    }
}
