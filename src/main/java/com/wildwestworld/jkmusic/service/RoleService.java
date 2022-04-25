package com.wildwestworld.jkmusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleDto;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleUpdateRequest;

import java.util.List;

public interface RoleService {

    void deleteRoleByID(String id);


    RoleDto createRole(RoleCreateRequest roleCreateRequest);

    RoleDto updateRoleById(String id, RoleUpdateRequest roleUpdateRequest);

    List<RoleDto> getRoleList(String searchWord);

//    List<RoleDto> getRoleSelectionList(String searchWord);


    IPage<RoleDto> getRolePage(Integer pageNum, Integer pageSize, String searchWord);
}
