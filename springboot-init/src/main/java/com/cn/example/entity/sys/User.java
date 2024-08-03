package com.cn.example.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.example.common.DataEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>系统用户 - 实体类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

/**
 * 实现UserDetails类
 * UserDetails为SpringSecurity中的用户类
 */
@Data
@NoArgsConstructor
@Accessors
@TableName(value = "sys_user")
public class User extends DataEntity implements UserDetails {

    private String username;        // 用户名
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;        // 密码
    private String nickname;        // 昵称
    private String email;           // 邮箱
    private String avatarUrl;       // 头像地址
    private String gender;          // 性别
    private String wechatOpenid;    // 微信OPENID
    private Date lastLoginTime;     // 最后一次登录时间

    @TableField(exist = false)
    private List<Role> roleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         * 通过数据库获取用户角色后
         * 通过setter() 注入用户
         */
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
