package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/01
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiLlsI_mmI4iLCJpYXQiOjE1ODgyODY2NjEsImNvbXBhbnlJZCI6IjEyMzQ1Njc4OSIsImNvbXBhbnlOYW1lIjoi5aSp5rSl5a6J6ZSQ5o235pyJ6ZmQ5YWs5Y-4In0.5Xm3OoXcwabdMgbtso5smA2wvk5jW0BgUI131mavbwI";
        Claims claim = Jwts.parser().setSigningKey("ihrm")
                .parseClaimsJws(token)
                .getBody();
        //私有数据都存放在 claim中
        System.out.println(claim.getId());
        System.out.println(claim.getSubject());
        System.out.println(claim.getIssuedAt());

        //JWT存储数据时，解析自定义存储数据
        String companyId = (String)claim.get("companyId");
        String companyName = (String)claim.get("companyName");
        System.out.println(companyId);
        System.out.println(companyName);
        /**
         * ss: jwt签发者
         * sub: jwt所面向的用户
         * aud: 接收jwt的一方
         * exp: jwt的过期时间，这个过期时间必须要大于签发时间
         * nbf: 定义在什么时间之前，该jwt都是不可用的.
         * iat: jwt的签发时间
         * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
         *
         *  headers: {
         *     'Authorization': 'Bearer ' + token
         *   }
         *
         *   // $Signature
         * HS256(Base64(Header) + "." + Base64(Payload), secretKey)
         *
         * // JWT
         * JWT = Base64(Header) + "." + Base64(Payload) + "." + $Signature
         */
    }
}