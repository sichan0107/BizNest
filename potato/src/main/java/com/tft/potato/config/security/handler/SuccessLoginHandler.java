package com.tft.potato.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tft.potato.config.security.provider.JwtProvider;
import com.tft.potato.config.security.service.TokenService;
import com.tft.potato.rest.user.dto.LoginResponseDto;
import com.tft.potato.rest.user.entity.User;
import com.tft.potato.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    private final RedisTemplate<String, String> redisTemplate;

    private final TokenService tokenService;

/*
    소셜로그인 방식 참고 : https://hoons-dev.tistory.com/141
 */

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String successLogin = "N";
        String loginResponseJsonTxt = "";
        String accessToken = "";
        String refreshToken = "";
        User user = null;

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");
        String role = oAuth2User.getAuthorities().stream().findFirst()
                            .orElseThrow(IllegalAccessError::new)
                            .getAuthority();

        try{
            if(StringUtils.isNotBlank(email)){
                user = userService.getUserByEmail(email);
                accessToken = jwtProvider.generateAccessToken(authentication); // client에게 반환
                refreshToken = jwtProvider.generateRefreshToken(authentication, accessToken);
                successLogin = "Y";

                tokenService.saveRefreshToken(user.getUserId(), refreshToken);

                log.info("new generated accesstoken : {}", accessToken);

            }else{
                log.error("wrong email. Fail to generate accesstoken : {}", email);
            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .successLogin(successLogin)
                    .email(email)
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .genderCd(user.getGenderCd())
                    .userProfileUrl(user.getUserProfileUrl())
                    .userName(username)
                    .birthday(user.getBirthday())
                    .role(role)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            loginResponseJsonTxt = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginResponseDto);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(loginResponseJsonTxt);
            out.flush();
        }



    }


}
