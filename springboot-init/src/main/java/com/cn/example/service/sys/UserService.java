package com.cn.example.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.example.entity.sys.User;

public interface UserService extends IService<User> {
    void addUser(User user);
    void updateUser(User user);
    void updatePassword(String userId, String password);
    void deleteUser(String userId);

    User get(String id);
    User getUserOfUsername(String username);
    Page<User> listPage(User user, Integer pageNum, Integer pageSize);
}
