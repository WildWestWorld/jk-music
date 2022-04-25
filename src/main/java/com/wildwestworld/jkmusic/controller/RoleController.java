package com.wildwestworld.jkmusic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.repository.MusicRepository;
import com.wildwestworld.jkmusic.repository.RoleRepository;
import com.wildwestworld.jkmusic.service.MusicService;
import com.wildwestworld.jkmusic.service.RoleService;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleDto;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagUpdateRequest;
import com.wildwestworld.jkmusic.transport.vo.RoleVo;
import com.wildwestworld.jkmusic.transport.vo.TagVo;
import com.wildwestworld.jkmusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleRepository roleRepository;

    @GetMapping
    public List<RoleVo> getTagList(@RequestParam(defaultValue = "")String searchWord){

        List<RoleDto> roleList = roleService.getRoleList(searchWord);
        List<RoleVo> roleVoList = roleList.stream().map(roleRepository::roleToVo).collect(Collectors.toList());
        return roleVoList;
    }


    @PostMapping
    public RoleVo createRole(@Validated @RequestBody RoleCreateRequest roleCreateRequest){
        RoleDto roleDto = roleService.createRole(roleCreateRequest);

        RoleVo roleVo = roleRepository.roleToVo(roleDto);

        return roleVo;
    }

    @PutMapping("/{id}")
    public RoleVo updateRole(@Validated @PathVariable String id, @RequestBody RoleUpdateRequest roleUpdateRequest){
        RoleDto roleDto = roleService.updateRoleById(id, roleUpdateRequest);
        RoleVo roleVo = roleRepository.roleToVo(roleDto);
        return roleVo;
    }

    //Result文件在utils里面 就是个简单的返回值，无聊可以看看
    @DeleteMapping("/{id}")
    Result<?> deleteRoleByID(@PathVariable String id){
        roleService.deleteRoleByID(id);
        return Result.success();
    }


    //分页
    @GetMapping("/pages")
    public IPage<RoleVo> getRolePage(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "")String searchWord){
        IPage<RoleDto> roleDtoPage = roleService.getRolePage(pageNum, pageSize, searchWord);

        List<RoleDto> roleDtoList = roleDtoPage.getRecords();

        List<RoleVo> roleVoList = roleDtoList.stream().map(roleRepository::roleToVo).collect(Collectors.toList());


        IPage<RoleVo> roleVoPage =new Page<>(pageNum,pageSize);
        roleVoPage.setRecords(roleVoList);
        roleVoPage.setCurrent(roleDtoPage.getCurrent());
        roleVoPage.setTotal(roleDtoPage.getTotal());
        roleVoPage.setSize(roleDtoPage.getSize());

        return  roleVoPage;
    }
}
