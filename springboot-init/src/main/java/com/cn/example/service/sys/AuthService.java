package com.cn.example.service.sys;

import com.cn.example.entity.sys.User;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(User user);
}
