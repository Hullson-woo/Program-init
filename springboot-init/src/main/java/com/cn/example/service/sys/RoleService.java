package com.cn.example.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.example.entity.sys.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(String roleId);

    void grantRole4User(String roleId, String userIds);

    Role get(String id);
    Page<Role> listPage(Role role, Integer pageNum, Integer pageSize);
    List<Role> listRoleOfUser(String userId);
}
