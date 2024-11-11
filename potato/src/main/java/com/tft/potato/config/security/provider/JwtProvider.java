package com.tft.potato.config.security.provider;

import antlr.TokenStreamException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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


    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;

    // 빈이 생성이 되고 의존성 주입 받은 secret값을 Base64 Decode해서 key변수에 할당
    @PostConstruct
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
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

    public boolean validateToken(String token) {
        try {
            // setSigningKey 메소드는 서명을 파싱하고 검증할 때 사용한다.
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                         .build()
                         .parseSignedClaims(token)
                         .getPayload();

            /*
            parseSignedClaims() 메소드가 검증하는 부분은 다음과 같다. Claim class는 JWT를 파싱한 결과이다.
            1. 원래 토큰이 발급된 이후 조작되지 않았는지
            2. 만료 시간 검증
            3. 발급 시간 확인
            4. 유효 시간 확인
            5. JWT 포맷 확인
             */

            // verifyWith 메소드는 JWT를 생성할 때 사용할 때 사용한다.
            //Jwts.parser().verifyWith(secretKey)
            //             .build()
            //             .parseSignedClaims(token)
            //             .getPayload();

            return true;

        } catch (IllegalArgumentException e) {
            log.error("");
            return false;
        } catch (JwtException e){
            log.error("Error ocuured during validating token. Retry to get new token.");
            return false;
        }
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
//
//    // 3. accessToken 재발급
//    public String reissueAccessToken(String accessToken) {
//        if (StringUtils.hasText(accessToken)) {
//            Token token = tokenService.findByAccessTokenOrThrow(accessToken);
//            String refreshToken = token.getRefreshToken();
//
//            if (validateToken(refreshToken)) {
//                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
//                tokenService.updateToken(reissueAccessToken, token);
//                return reissueAccessToken;
//            }
//        }
//        return null;
//    }
//
//    public boolean validateToken(String token) {
//        if (!StringUtils.hasText(token)) {
//            return false;
//        }
//
//        Claims claims = parseClaims(token);
//        return claims.getExpiration().after(new Date());
//    }
//
    private Claims parseClaims(String token) throws TokenStreamException{
        try {
            //return Jwts.parser().verifyWith(secretKey).build()
            //        .parseSignedClaims(token).getPayload();

            return Jwts.parser().build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (MalformedJwtException e) {
            throw new TokenStreamException("This is invalid token : " );
        } catch (SecurityException e) {
            throw new SecurityException("This signature is invalid");
        }
    }
}
