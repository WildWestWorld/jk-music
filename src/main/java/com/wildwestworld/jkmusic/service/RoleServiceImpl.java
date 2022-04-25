package com.wildwestworld.jkmusic.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.Music;
import com.wildwestworld.jkmusic.entity.Role;
import com.wildwestworld.jkmusic.entity.Tag;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import com.wildwestworld.jkmusic.mapper.MusicMapper;
import com.wildwestworld.jkmusic.mapper.RoleMapper;
import com.wildwestworld.jkmusic.repository.RoleRepository;
import com.wildwestworld.jkmusic.transport.dto.Music.MusicDto;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleCreateRequest;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleDto;
import com.wildwestworld.jkmusic.transport.dto.Role.RoleUpdateRequest;
import com.wildwestworld.jkmusic.transport.dto.Tag.TagDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleRepository roleRepository;

    @Override
    public List<RoleDto> getRoleList(String searchWord) {

        List<Role> roleList = roleMapper.getRoleList(searchWord);

        List<RoleDto> roleDtoList = roleList.stream().map(roleRepository::roleToDto).collect(Collectors.toList());
        return roleDtoList;
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleCreateRequest roleCreateRequest) {
        //先给他转化成Entity
        Role roleEntity = roleRepository.createRoleEntity(roleCreateRequest);
        System.out.println(roleEntity);

        //将实体类插入数据
        roleMapper.insert(roleEntity);



        System.out.println(roleEntity.getId());


//        roleMapper.insertArtistRole(roleEntity);

        if (CollUtil.isNotEmpty(roleCreateRequest.getUserIdList())) {
            roleMapper.batchInsertRoleUser(roleEntity, roleCreateRequest.getUserIdList());
        }
//
//        if (CollUtil.isNotEmpty(roleCreateRequest.getPlayListIdList())) {
//            roleMapper.batchInsertRolePlayList(roleEntity, roleCreateRequest.getPlayListIdList());
//        }

        //然后转化为Dto
        RoleDto roleDto = roleRepository.roleToDto(roleEntity);
        return roleDto;
    }

    @Override
    @Transactional
    public RoleDto updateRoleById(String id, RoleUpdateRequest roleUpdateRequest) {
        Role role = roleMapper.selectRoleById(id);
        if(role == null){
            //自定义的异常类  自定义的异常类的信息在exception里面
            throw new BizException(BizExceptionType.Role_NOT_FOUND);
        }

        //如果musicUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(roleUpdateRequest.getName())){
            //设置名字
            role.setName(roleUpdateRequest.getName());
        }

        //如果musicUpdateRequest的Name不是空的
        if (StrUtil.isNotEmpty(roleUpdateRequest.getTitle())){
            //设置名字
            role.setTitle(roleUpdateRequest.getTitle());
        }


//更新音乐与歌手的关系
        if (roleUpdateRequest.getUserIdList() != null ) {
            if (CollUtil.isNotEmpty(roleUpdateRequest.getUserIdList())) {
                List<String> originIdList;
                if (role.getUserList() != null & CollUtil.isNotEmpty(role.getUserList())) {
                    //方案1:
                    //根据前端传过来的给的roleList的长度来更新数据，一样长就全都更新，短了就更新后删除差值数量的数据，长了就更新后再新增

                    List<String> IdList = role.getUserList().stream().map(item -> item.getId()).collect(Collectors.toList());
                    //新增的Id List - 原始的Id List = 两个List不同的id/我们需要新增的IdList
                    originIdList = IdList;
                } else {
                    List<String> IdList = null;
                    originIdList = IdList;
                }

                //也就是需要新增的Id
                //CollUtil.subtractToList(A,B)比较数组，A数组-B数组，然后优先保留A数组内容

                //需要插入的Id数组
                List<String> needInsertIdList = CollUtil.subtractToList(roleUpdateRequest.getUserIdList(), originIdList);


                //需要删除的Id数组
                List<String> needDeleteIdList = CollUtil.subtractToList(originIdList, roleUpdateRequest.getUserIdList());

                if (needDeleteIdList.size() != 0) {
                    roleMapper.batchDeleteRoleUserById(role, needDeleteIdList);
                }


                if (needInsertIdList.size() != 0) {
                    roleMapper.batchInsertRoleUser(role, needInsertIdList);
                }


            }else{
                if (role.getUserList() != null & CollUtil.isNotEmpty(role.getUserList())) {
                    roleMapper.deleteAllRoleUserById(role);
                }
            }
        }




        //更新user
        roleMapper.updateById(role);

        //再次查询user
        Role updateRole = roleMapper.selectRoleById(id);

        RoleDto roleDto = roleRepository.roleToDto(updateRole);
        return roleDto;
    }


    @Override
    @Transactional

    public void deleteRoleByID(String id) {
        Role role = roleMapper.selectRoleById(id);

        if (role.getUserList() !=null & !CollUtil.isEmpty(role.getUserList()) ) {
            roleMapper.deleteAllRoleUserById(role);
        }

//        if (role.getPlayList() !=null & !CollUtil.isEmpty(role.getPlayList()) ) {
//            roleMapper.deleteAllRolePlayListById(role);
//        }
        roleMapper.deleteById(role);
    }

    @Override
    public IPage<RoleDto> getRolePage(Integer pageNum, Integer pageSize, String searchWord) {
//        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery();
//        if (StrUtil.isNotBlank(searchWord)){
//            wrapper.eq(Role::getName,searchWord);
//        }



        IPage<Role> rolePage = roleMapper.getPage(new Page<>(pageNum, pageSize), searchWord);


        List<Role> roleList = rolePage.getRecords();

        List<RoleDto> roleDtoList = roleList.stream().map(roleRepository::roleToDto).collect(Collectors.toList());
//        for (RoleDto roleDto : roleDtoList) {
//            FileDto fileDto = roleDto.getFile();
//            if (fileDto !=null){
//                String hashKey = fileDto.getHashKey();
//
//            }
//        }

        IPage<RoleDto> roleDtoPage = new Page<>(pageNum,pageSize);

        roleDtoPage.setRecords(roleDtoList);

        roleDtoPage.setCurrent(rolePage.getCurrent());
        roleDtoPage.setTotal(rolePage.getTotal());
        roleDtoPage.setSize(rolePage.getSize());

        return roleDtoPage;
    }
}
