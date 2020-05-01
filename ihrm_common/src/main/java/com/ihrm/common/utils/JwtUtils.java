package com.ihrm.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/01
 */
@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtUtils {
    //签名私钥（盐）
    private String key;
    ////签名的失效时间
    private Long ttl;

    /**
     * 设置认证token
     * id:登录用户id
     * subject：登录用户名
     */
    public String createJwt(String userId, String userName, Map<String, Object> map) {
        //1.设置失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(userId).setSubject(userName)
                .setIssuedAt(new Date())
                //自定义claim
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(exp));
        //创建token
        String token = jwtBuilder.compact();
        return  token;
    }

    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}