package com.cn.example.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.example.entity.sys.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    void deleteRole(@Param("roleId") String roleId);
    void grantRole4User(@Param("roleId") String roleId, @Param("userIds") List<String> userIds);

    List<Role> listPage(Page<Role> page, @Param("role") Role role);
    List<Role> listRoleOfUser(@Param("userId") String userId);
}
