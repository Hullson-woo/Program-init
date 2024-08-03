package com.cn.example.controller.sys;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import com.cn.example.entity.sys.User;
import com.cn.example.service.sys.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>鉴权- api</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-29
 * @since 1.0
 */
@RestController
@RequestMapping("/sys/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("login")
    public Result login(@RequestBody User user) {
        return ResultUtils.success(authService.login(user));
    }
}
