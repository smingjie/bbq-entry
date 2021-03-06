package com.micro.bbqentry.general.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类 解析 or 生成
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    /**
     * 过期时间，毫秒，60分钟
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000L;
    /**
     * 私钥
     */
    private static final String SECRET = "privateKey";
    /**
     * 算法
     */
    private static final String ALG = "HS256";

    /**
     * 算法选择
     */
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    /**
     * token 生成
     *
     * @param map 自定义需要使用的用户信息
     * @return token字符串
     */
    public static String createToken(Map<String, String> map) throws BusinessException {
        log.info("进入JwtUtility工具类的createToken()方法，准备生成token串");
        //jwt builder
        String token = null;
        JWTCreator.Builder builder = JWT.create();
        try {
            //1.头部分(header)
            Map<String, Object> headerMap = new HashMap<>(2, 1);
            headerMap.put("alg", ALG);
            headerMap.put("typ", "JWT");
            builder.withHeader(headerMap);

            //2.有效载荷部分(playload)
            //签发时间
            Date istDate = new Date();
            //过期时间
            Date expireDate = new Date(istDate.getTime() + EXPIRE_TIME);
            builder.withIssuedAt(istDate).withExpiresAt(expireDate);
            //jwt中加入自定义信息
            map.forEach(builder::withClaim);

            //3.签名部分(signature)
            token = builder.sign(ALGORITHM);
            log.info("jwt：{}", token);
            return token;
        } catch (Exception ex) {
            log.info("token生成失败:{}", ex.getMessage());
            throw new BusinessException(ResponseEnum.TOKEN_CREATE_ERROR);
        }

    }


    /**
     * token 解析
     *
     * @param token token字符串
     * @return 解析的自定义信息结果
     */
    public static Map<String, String> parseToken(String token) throws BusinessException {
        try {
            DecodedJWT decodedJwt = getDecodedJwt(token);
            //获取 claims，包括了有效载荷部分的所有，不仅只有自定义的信息
            Map<String, Claim> claimMap = decodedJwt.getClaims();
            Map<String, String> resultMap = new HashMap<>(claimMap.size(), 1);
            //遍历并填充 result ，其中非String的值会被置null
            claimMap.forEach((k, v) -> resultMap.put(k, v.asString()));
            return resultMap;
        } catch (TokenExpiredException e) {
            log.error("Token已过期: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_EXPIRED);
        } catch (JWTCreationException e) {
            log.error("Token没有被正确构造: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_CREATE_ERROR);
        } catch (SignatureVerificationException e) {
            log.error("签名校验失败: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_SIGN_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("非法参数异常: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_PHASE_ERROR);
        }
    }

    /**
     * 获取过期时间
     */
    public static Date getExpiredTime(String token) {
        return getDecodedJwt(token).getExpiresAt();
    }


    /**
     * 获取 DecodedJWT
     */
    private static DecodedJWT getDecodedJwt(String token) throws BusinessException {
        try {
            JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
            return jwtVerifier.verify(token);
        } catch (Exception ex) {
            log.info("token校验解析失败:{}", ex.getMessage());
            throw new BusinessException(ResponseEnum.TOKEN_PHASE_ERROR);
        }
    }

}
