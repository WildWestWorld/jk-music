package com.wildwestworld.jkmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wildwestworld.jkmusic.entity.PlayList;
import com.wildwestworld.jkmusic.entity.Role;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> getPage(Page<Role> page , @Param("name") String name);

    List<Role> getRoleList(@Param("name") String name);

//    List<Role> getPlaySelectionList(@Param("name") String name);



    Role selectRoleById(@Param("id") String id);


    int batchInsertRoleUser(@Param("role")Role entity,@Param("needInsertIdList") List<String> needInsertIdList);

    int batchDeleteRoleUserById(@Param("role")Role entity,@Param("needDeleteIdList") List<String> needDeleteIdList);


    int deleteAllRoleUserById(@Param("role") Role entity);

    //获取插入后才能自动生成的id
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role entity);
}
