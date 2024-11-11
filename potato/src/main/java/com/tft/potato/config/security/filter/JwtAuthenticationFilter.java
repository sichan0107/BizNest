package com.tft.potato.config.security.filter;

import antlr.TokenStreamException;
import com.tft.potato.config.security.provider.JwtProvider;
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


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request); // Request의 Header에서 "Authorization : Beared ~" 부분의 토큰 값을 가져온다.
        if (token != null && jwtProvider.validateToken(token)) { // Token의 유효성
            Authentication auth = null;
            try {
                auth = jwtProvider.getAuthentication(token);
            } catch (TokenStreamException e) {
                log.error("JwtAuthenticationFilter Authentication error : {}", e,toString());
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
