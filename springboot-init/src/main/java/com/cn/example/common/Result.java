package com.cn.example.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * <p>统一结果返回实体类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {
    // 返回状态码
    private Integer code = HttpStatus.OK.value();
    // 返回提示信息
    // 用于接收非实体类信息 如ID或字符串
    private String message;
    // 返回异常信息
    private String error;
    // 返回实体类对象
    private T data;
}

