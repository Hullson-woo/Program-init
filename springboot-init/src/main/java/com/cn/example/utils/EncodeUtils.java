package com.cn.example.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>加密工具类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
public class EncodeUtils {

    /**
     * 采用SpringSecurity的BCryptPasswordEncoder加密策略
     * @param password  密码
     * @return          加密字符串
     */
    public static String BcryptEncode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * BCryptPasswordEncoder密码校验
     * @param password          输入密码
     * @param encodePassword    加密密码
     * @return                  匹配结果
     */
    public static Boolean BcryptMatch(String password, String encodePassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, encodePassword);
    }
}
