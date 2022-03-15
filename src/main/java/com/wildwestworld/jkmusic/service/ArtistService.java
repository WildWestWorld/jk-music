package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.Artist;
import com.wildwestworld.jkmusic.entity.PlayList;

public interface ArtistService {
    IPage<Artist> getArtistPage(Integer pageNum, Integer pageSize, String searchWord);

}
