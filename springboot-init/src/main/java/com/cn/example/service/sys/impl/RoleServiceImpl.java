package com.cn.example.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.example.common.Constant;
import com.cn.example.common.exception.ServiceException;
import com.cn.example.entity.sys.Role;
import com.cn.example.mapper.sys.RoleMapper;
import com.cn.example.service.sys.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * <p>系统角色管理 - 业务层</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-27
 * @since 1.0
 */

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 系统角色管理 - 新增角色
     * @param role  角色实体
     */
    @Override
    public void addRole(Role role) {
        if (role == null) {
            throw new ServiceException(500, "role cannot by empty");
        }

        Assert.isTrue(StringUtils.isNotBlank(role.getRoleName()), "the role name cannot be null.");
        Assert.isTrue(StringUtils.isNotBlank(role.getCode()), "the role code cannot be null.");
        Assert.isTrue(StringUtils.isNotBlank(role.getRoleType()), "the role type cannot be null.");

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getCode, role.getCode());
        wrapper.eq(Role::getRoleType, role.getRoleType());
        wrapper.eq(Role::getDelFlag, Constant.DELETE_FLAG_NOMARL);
        Role dbRole = baseMapper.selectOne(wrapper);

        if (dbRole != null) {
            throw new ServiceException(500, "sorry, the role already exists");
        }

        role.preInsert();
        baseMapper.insert(role);

    }

    /**
     * 系统角色管理 - 修改角色
     * @param role  角色实体
     */
    @Override
    public void updateRole(Role role) {
        role.preUpdate();
        baseMapper.updateById(role);
    }

    /**
     * 系统角色管理 - 删除角色
     * @param roleId    角色ID
     */
    @Override
    public void deleteRole(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new ServiceException(500, "role id cannot be empty");
        }

        baseMapper.deleteRole(roleId);
    }

    /**
     * 系统角色管理 - 授权用户角色
     * @param roleId    角色ID
     * @param userIds   用户ID列表
     */
    @Override
    public void grantRole4User(String roleId, String userIds) {
        List<String> userIdList = Arrays.asList(userIds.split(","));
        baseMapper.grantRole4User(roleId, userIdList);
    }

    /**
     * 系统角色管理 - 根据ID获取角色信息
     * @param id    角色ID
     * @return      角色信息
     */
    @Override
    public Role get(String id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);
        wrapper.eq(Role::getDelFlag, Constant.DELETE_FLAG_NOMARL);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 系统角色管理 - 获取角色数据列表（分页）
     * @param role      角色实体
     * @param pageNum   页码
     * @param pageSize  行数
     * @return          角色数据列表
     */
    @Override
    public Page<Role> listPage(Role role, Integer pageNum, Integer pageSize) {
        log.info("## /sys/role/listPage ## \t {}", role);

        if (role == null) {
            role = new Role();
        }
        Page<Role> page = new Page<>(pageNum, pageSize);
        page.setRecords(baseMapper.listPage(page, role));
        return page;
    }

    /**
     * 系统角色管理 - 获取用户角色列表
     * @param userId    用户ID
     * @return          用户角色列表
     */
    @Override
    public List<Role> listRoleOfUser(String userId) {
        return baseMapper.listRoleOfUser(userId);
    }
}
