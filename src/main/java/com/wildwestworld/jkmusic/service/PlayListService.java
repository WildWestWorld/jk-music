package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.entity.PlayList;

public interface PlayListService {

     IPage<PlayList> getPlayListPage(Integer pageNum, Integer pageSize, String searchWord);


}
