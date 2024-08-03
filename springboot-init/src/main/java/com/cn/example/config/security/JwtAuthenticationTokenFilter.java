package com.cn.example.config.security;

import com.cn.example.common.Constant;
import com.cn.example.common.exception.ServiceException;
import com.cn.example.entity.sys.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>JWT token认证过滤器</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-28
 * @since 1.0
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(Constant.AUTH_HEADER);
        log.info("## token ## \t {}", token);

        /*
         * 如果请求头不存在token  或者  token不以 "Bearer "开头
         * 则放行，进入下一层过滤器
         */
        if (token == null || !token.startsWith(Constant.AUTH_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        /*
         * 如果存在token  则进行token解析
         */
        User user = jwtUtils.getUserFromToken(token);

        if (user == null) {
            throw new ServiceException(403, "you are not logged in yet.");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
