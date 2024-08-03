package com.cn.example.common.exception;

import lombok.Data;

/**
 * <p>自定义异常</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@Data
public class ServiceException extends RuntimeException {
    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
