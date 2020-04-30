package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/01
 */
public class CreateJwtTest<psvm> {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder1 = Jwts.builder().setId("123456")
                .setSubject("小明") //签名
                .setIssuedAt(new Date())  //签名签发时间；
                .signWith(SignatureAlgorithm.HS256, "ihrm")
                 //JWT存储数据时，自定义存储数据
                .claim("companyId","123456789")
                .claim("companyName","天津安锐捷有限公司");

        String  jwtBuilder = jwtBuilder1.compact();
        System.out.println(jwtBuilder);
    }
}