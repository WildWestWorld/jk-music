package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistDto;
import com.wildwestworld.jkmusic.transport.dto.Artist.ArtistRecommendRequest;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListDto;
import com.wildwestworld.jkmusic.transport.dto.PlayList.PlayListRecommendRequest;

public interface PlayListService {

     IPage<PlayList> getPlayListPage(Integer pageNum, Integer pageSize, String searchWord);


     PlayListDto changeToRecommend(String id, PlayListRecommendRequest playListRecommendRequest);

     PlayListDto cancelRecommend(String id);

}
