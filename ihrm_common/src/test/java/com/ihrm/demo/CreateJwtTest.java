package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 创建token
 */
public class CreateJwtTest {
    public static void main(String[] args) {
        long now = System.currentTimeMillis(); //当前时间
        long exp = now + 1000*60*60; //过期时间为一小时
        JwtBuilder jwtBuilder = Jwts.builder().setId("888")
                .setSubject("大白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "oyrain")
                .setExpiration(new Date(exp))
                .claim("roles","admin") //自定义claims存储数据
                .claim("logo","logo.png");
        String token = jwtBuilder.compact();
        System.out.println(token);
    }
}
