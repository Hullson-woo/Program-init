package com.cn.example.controller.sys;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import com.cn.example.entity.sys.User;
import com.cn.example.service.sys.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>系统用户管理 - api</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("add")
    public Result addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResultUtils.success();
    }

    @PutMapping("update")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResultUtils.success();
    }

    @PutMapping("updatePassword")
    public Result updatePassword(@RequestParam("userId") String userId,
                                 @RequestParam("password") String password) {
        userService.updatePassword(userId, password);
        return ResultUtils.success();
    }

    @DeleteMapping("delete")
    public Result deleteUser(@RequestParam("id") String id) {
        userService.deleteUser(id);
        return ResultUtils.success();
    }

    @GetMapping("get")
    public Result get(@RequestParam("id") String id) {
        return ResultUtils.success(userService.get(id));
    }

    @GetMapping("getOfUsername")
    public Result getOfUsername(@RequestParam("username") String username) {
        return ResultUtils.success(userService.getUserOfUsername(username));
    }

    @PostMapping("listPage")
    public Result listPage(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestBody User user) {
        return ResultUtils.success(userService.listPage(user, pageNum, pageSize));
    }
}
