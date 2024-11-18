package com.tft.potato.config.security.service;

import com.tft.potato.aop.exception.CustomException;
import com.tft.potato.config.security.provider.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProvider jwtProvider;


    public void saveRefreshToken(String userId, String refreshToken) {
        String refreshKey = jwtProvider.getRefreshTokenPrefix() + userId;
        redisTemplate.opsForValue().set(refreshKey, refreshToken, jwtProvider.getRefreshTokenExpireTime(), TimeUnit.MILLISECONDS);
    }

    // Redis에서 Refresh Token 조회
    public String getRefreshToken(String userId) {
        String refreshKey = jwtProvider.getRefreshTokenPrefix() + userId;
        return redisTemplate.opsForValue().get(refreshKey);
    }

    public String reissueAccessToken()  {
        String accessToken = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        String refreshToken = getRefreshToken(userId);

        try {
            if(StringUtils.isNotBlank(refreshToken) && jwtProvider.validateRefreshToken(refreshToken)){
                accessToken = jwtProvider.generateAccessToken(authentication);
            }
        }catch (CustomException e){
            log.error("Refresh token is expired.");
            throw e;
        }

        return accessToken;
    }

    // Redis에서 Refresh Token 삭제
    public void deleteRefreshToken(String userId) {
        String refreshKey = jwtProvider.getRefreshTokenPrefix() + userId;
        redisTemplate.delete(refreshKey);
    }
}
