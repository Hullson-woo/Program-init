package com.cn.example.controller.sys;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import com.cn.example.entity.sys.Role;
import com.cn.example.service.sys.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>系统用户管理 - api</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-27
 * @since 1.0
 */

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("add")
    public Result addRole(@RequestBody Role role) {
        roleService.addRole(role);
        return ResultUtils.success();
    }

    @PutMapping("update")
    public Result updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResultUtils.success();
    }

    @DeleteMapping("delete")
    public Result deleteRole(@RequestParam("id") String id) {
        roleService.deleteRole(id);
        return ResultUtils.success();
    }

    @PostMapping("grantRole4User")
    public Result grantRole4User(@RequestParam("roleId") String roleId,
                                 @RequestParam("userId") String userId) {
        roleService.grantRole4User(roleId, userId);
        return ResultUtils.success();
    }

    @GetMapping("get")
    public Result get(@RequestParam("id") String id) {
        return ResultUtils.success(roleService.get(id));
    }

    @PostMapping("listPage")
    public Result listPage(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestBody Role role) {
        return ResultUtils.success(roleService.listPage(role, pageNum, pageSize));
    }

    @PostMapping("listOfUser")
    public Result listRoleOfUser(@RequestParam("userId") String userId) {
        return ResultUtils.success(roleService.listRoleOfUser(userId));
    }
}
