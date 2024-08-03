package com.cn.example.utils;

import java.util.UUID;

/**
 * <p>ID生成器</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
public class GenerateId {

    public static String uuidGen() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

}
