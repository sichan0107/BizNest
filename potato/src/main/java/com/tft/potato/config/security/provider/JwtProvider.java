package com.tft.potato.config.security.provider;

import antlr.TokenStreamException;
import com.tft.potato.aop.exception.CustomException;
import com.tft.potato.aop.exception.ErrorEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.key}")
    private String secretKey;

    private Key key;
    private static final String ClAME_KEY = "auth";

    private final String REDIS_REF_TOKEN_PREFIX = "potato-refresh-user-id : ";


    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;

    // 빈이 생성이 되고 의존성 주입 받은 secret값을 Base64 Decode해서 key변수에 할당
    @PostConstruct
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public long getRefreshTokenExpireTime(){
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

    public String getRefreshTokenPrefix(){
        return REDIS_REF_TOKEN_PREFIX;
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }

    //1. refresh token 발급
    public String generateRefreshToken(Authentication authentication, String accessToken) {
        String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
        //tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken); // redis에 저장
        return refreshToken;
    }

    private String generateToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(ClAME_KEY, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(key)
                .compact();
    }


    public Authentication getAuthentication(String token) throws TokenStreamException {
        Claims claims = parseClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        // 2. security의 User 객체 생성 (일단은 sub 객체만 들고 있는걸로)
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(
                claims.get(ClAME_KEY).toString()));
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken); // 만료되면 ExpiredJwtException 발생

            Claims claims = Jwts.parser().build().parseSignedClaims(refreshToken).getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            log.info("Refresh Token has expired. ");
            throw new CustomException(ErrorEnum.EXPIRED_REFRESH_TOKEN);
        } catch (JwtException e) {
            log.info("Invalid Refresh Token ");
            throw e;
        }

    }

    public Claims parseClaims(String token) throws TokenStreamException {
        try {
            return Jwts.parser().build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.info("This access token is expired. Start making a new token.");
            throw e;
        } catch (MalformedJwtException e) {
            throw new TokenStreamException("This is invalid token : " + token, e);
        } catch (SecurityException e) {
            throw new SecurityException("This signature is invalid", e);
        }
    }
}
