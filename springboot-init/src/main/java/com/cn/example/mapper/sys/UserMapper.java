package com.cn.example.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.example.entity.sys.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    void updatePassword(@Param("userId") String userId, @Param("password") String password);
    void deleteUser(@Param("userId") String userId);

    User getUserOfUsername(@Param("username") String username);
    List<User> listPage(Page<User> page, @Param("user") User user);

}
