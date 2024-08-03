package com.cn.example.config.security;

import com.cn.example.entity.sys.User;
import com.cn.example.utils.GenerateId;
import com.cn.example.utils.JedisUtils;
import com.google.common.collect.Maps;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * <p>JWT 工具类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-28
 * @since 1.0
 */
@Component
public class JwtUtils {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.prefix}")
    private String prefix;
    @Value("${jwt.iss}")
    private String iss;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.validty}")
    private Duration validty;
    @Value("${jwt.cachePrefix}")
    private String cachePrefix;

    public String createToken(User user) {
        String uuid = GenerateId.uuidGen();
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("cacheId", uuid);

        long expireMillis = System.currentTimeMillis() + validty.toMillis();
        Date expireDate = new Date(expireMillis);

        String token = Jwts.builder()
                .setId(uuid)
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setIssuer(iss)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();

        Long cacheExpireMillis = validty.toMillis() / 1000;
        String cacheKey = cachePrefix + uuid;
        JedisUtils.setObject(cacheKey, user, cacheExpireMillis.intValue());

        return token;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        String subject = claims.getSubject();
        return subject;
    }

    public User getUserFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }

        String uuid = claims.get("cacheId").toString();
        String cacheKey = cachePrefix + uuid;
        User user = (User) JedisUtils.getObject(cacheKey);
        return user;
    }

    public boolean isExpire(String token) {
        Claims claims = parseToken(token);
        return claims == null;
    }


    public Claims parseToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

}
