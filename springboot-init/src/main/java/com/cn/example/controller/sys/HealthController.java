package com.cn.example.controller.sys;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>服务健康状态 - api</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-29
 * @since 1.0
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @PostMapping("check")
    public Result checkHealth() {
        return ResultUtils.success();
    }
}
