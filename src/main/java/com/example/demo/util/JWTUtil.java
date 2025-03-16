package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final String KEY = "itFLy";

    //接收业务数据,生成token并返回
    public static String generateToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)//业务数据
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))//有效期
                .sign(Algorithm.HMAC256(KEY));//密钥
    }

    //接收token,验证token,并返回业务数据
    public static Map<String, Object> verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))//密钥
                .build()//创建验证对象
                .verify(token)//验证token
                .getClaim("claims")//获取业务数据
                .asMap();//转换为map
    }

}
