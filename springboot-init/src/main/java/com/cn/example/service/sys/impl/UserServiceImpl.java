package com.cn.example.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.example.common.Constant;
import com.cn.example.common.exception.ServiceException;
import com.cn.example.entity.sys.Role;
import com.cn.example.entity.sys.User;
import com.cn.example.mapper.sys.UserMapper;
import com.cn.example.service.sys.UserService;
import com.cn.example.utils.EncodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>系统用户管理 - 业务层</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 系统用户管理 - 新增用户
     * @param user  用户实体
     */
    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new ServiceException(500, "user cannot be empty.");
        }
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new ServiceException(500, "please check user info, username or password cannot be empty.");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        wrapper.eq(User::getDelFlag, Constant.DELETE_FLAG_NOMARL);
        User dbUser = baseMapper.selectOne(wrapper);

        if (dbUser != null) {
            throw new ServiceException(500, "sorry, the username already exists");
        }

        String password = user.getPassword();
        String encodePassword = EncodeUtils.BcryptEncode(password);
        user.setPassword(encodePassword);

        if (user.getNickname().isEmpty()) {
            user.setNickname("用户" + user.getUsername());
        }
        if (user.getGender().isEmpty()) {
            user.setGender(Constant.GENDER_SECRECY);
        }

        user.preInsert();
        baseMapper.insert(user);
    }

    /**
     * 系统用户管理 - 修改用户
     * @param user  用户实体
     */
    @Override
    public void updateUser(User user) {
        user.preUpdate();
        baseMapper.updateById(user);
    }

    @Override
    public void updatePassword(String userId, String password) {
        User user = get(userId);
        String encodePassword = EncodeUtils.BcryptEncode(password);
        baseMapper.updatePassword(user.getId(), encodePassword);
    }

    /**
     * 系统用户管理 - 删除用户
     * @param userId    用户ID
     */
    @Override
    public void deleteUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new ServiceException(500, "the user id is empty.");
        }
        baseMapper.deleteUser(userId);
    }

    /**
     * 系统用户管理 - 根据ID获取用户信息
     * @param id    用户ID
     * @return      用户信息
     */
    @Override
    public User get(String id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        wrapper.eq(User::getDelFlag, Constant.DELETE_FLAG_NOMARL);
        User user = baseMapper.selectOne(wrapper);
        if (user == null) {
            throw new ServiceException(500, "sorry, the user does not exist.");
        }
        return user;
    }

    /**
     * 系统用户管理 - 根据用户名获取用户信息
     * @param username  用户名
     * @return          用户信息
     */
    @Override
    public User getUserOfUsername(String username) {
        return baseMapper.getUserOfUsername(username);
    }

    /**
     * 系统用户管理 - 获取用户数据列表（分页）
     * @param user      用户实体
     * @param pageNum   页码
     * @param pageSize  行数
     * @return          用户数据列表
     */
    @Override
    public Page<User> listPage(User user, Integer pageNum, Integer pageSize) {
        log.info("## /sys/user/listPage ## \t {}", user);
        if (user == null) {
            user = new User();
        }
        Page<User> page = new Page<>(pageNum, pageSize);

        page.setRecords(baseMapper.listPage(page, user));
        return page;
    }

    /**
     * 重写SpringSecurity loadUserByUsername方法
     * 通过数据库查找用户进行身份验证
     * @param username                      用户名
     * @return                              SpringSecurity UserDetails对象（User）
     * @throws UsernameNotFoundException    用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("## loadUserByUsername ##");
        User user = getUserOfUsername(username);

        if (user == null) {
            throw new ServiceException(500, "sorry, the user doesn't exists");
        }

        List<Role> roleList = roleService.listRoleOfUser(user.getId());
        user.setRoleList(roleList);
        return user;
    }
}
