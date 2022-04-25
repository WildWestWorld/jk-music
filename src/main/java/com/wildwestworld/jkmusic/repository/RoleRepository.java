package com.wildwestworld.jkmusic.repository;

import com.wildwestworld.jkmusic.entity.Role;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.vo.RoleVo;
import com.wildwestworld.jkmusic.transport.vo.TagVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface RoleRepository {
    RoleDto roleToDto(Role role);

    RoleVo roleToVo(RoleDto roleDto);



    Role createRoleEntity (RoleCreateRequest roleCreateRequest);
}
