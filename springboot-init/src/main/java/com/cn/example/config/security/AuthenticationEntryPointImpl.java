package com.cn.example.config.security;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>认证失败处理器</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-29
 * @since 1.0
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("unauthorized");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = httpServletResponse.getWriter();
        String msg = "Unauthorized";
        if (e != null) {
            msg = e.getMessage();
        }

        Result<String> result = ResultUtils.returnCode(HttpServletResponse.SC_UNAUTHORIZED, msg);
        writer.write(result.toString());
    }
}
