package com.ihrm.common.utils;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

@ConfigurationProperties("jwt.config")
@Getter
@Setter
public class JwtUtils {

    //签名私钥
    private String key;
    //过期时间
    private long ttl;

    /**
     * 签发token
     * @param id
     * @param subject
     * @param map
     * @return
     */
    public String createJWT(String id, String subject, Map<String,Object> map){
        //1.设置失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        //3.根据map设置claims
        for(Map.Entry<String,Object> entry:map.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        if(ttl > 0){
            jwtBuilder.setExpiration(new Date(exp));
        }
        //4.创建token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token字符串获取claims
     * @param token
     * @return
     */
    public Claims parseJWT(String token){
        Claims claims  = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
