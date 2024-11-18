package com.tft.potato.config.security.filter;

import antlr.TokenStreamException;
import com.tft.potato.config.security.provider.JwtProvider;
import com.tft.potato.config.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }

    // Access Token 재발급해서 어떻게 보내주어야하나...
    private void setNewAccessTokenOfRequest(HttpServletRequest request, String newAccessToken){
        //request.getHeader("Authorization").
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request); // Request의 Header에서 "Authorization : Bearer ~" 부분의 토큰 값을 가져온다.
        Claims claims = null;
        try{

            if (token != null) { // Token의 유효성
                claims = jwtProvider.parseClaims(token);
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (TokenStreamException e){
            log.error("JwtAuthenticationFilter Authentication error : {}", e,toString());
        }catch (ExpiredJwtException e){
            String newAccessToken = tokenService.reissueAccessToken();
            response.setHeader("Authorization", newAccessToken);
        }catch (JwtException | IllegalArgumentException e){
            request.setAttribute("invalid", true);
        }


        filterChain.doFilter(request, response);
    }


}
