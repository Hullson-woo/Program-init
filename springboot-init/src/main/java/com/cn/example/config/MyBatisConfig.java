package com.cn.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>自定义MyBatis配置类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@Configuration
public class MyBatisConfig {
    /**
     * 自定义MyBatisPlus拦截器
     * 用于配置MySQL数据库类型的分页拦截
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
