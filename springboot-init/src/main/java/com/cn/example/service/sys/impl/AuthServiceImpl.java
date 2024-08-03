package com.cn.example.service.sys.impl;

import com.cn.example.common.exception.ServiceException;
import com.cn.example.config.security.JwtUtils;
import com.cn.example.entity.sys.User;
import com.cn.example.service.sys.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>系统认证auth - 业务层</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-28
 * @since 1.0
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public Map<String, Object> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        log.info("## authenticaion ## \t {}", authentication);
        if (authentication == null) {
            throw new ServiceException(500, "the username or password is incorrect");
        }

        User loginUser = (User) authentication.getPrincipal();
        String token = jwtUtils.createToken(user);

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("token", token);
        map.put("user", loginUser);

        return map;
    }
}
