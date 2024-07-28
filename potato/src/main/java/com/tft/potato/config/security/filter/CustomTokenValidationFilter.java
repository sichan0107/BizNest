package com.tft.potato.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tft.potato.rest.user.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;

@Slf4j

public class CustomTokenValidationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private  UserRepository userRepository;

    private Map<String, Object> getRequestBody(HttpServletRequest request) throws IOException {
        Map<String, Object> jsonRequest = null;

        if (request.getContentType() != null && request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
                && "POST".equals(request.getMethod())) {
            // JSON 데이터를 읽어오는 부분
            jsonRequest = objectMapper.readValue(request.getReader(), Map.class);
        }
        return jsonRequest;
    }


    /**
     * requestBodyMap 값
     * @key : String registrationId (ex: naver, google, kakao)
     * @key : String accessToken
     * @key : String idToken (google일 경우 존재함)
     *
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> requestBodyMap = null;
        String idTokenValue = "";
        String refreshTokenValue = "";

        try {
            requestBodyMap = getRequestBody(request);
            String registrationId = (String)requestBodyMap.get("registrationId");

            if(registrationId.equals("google")){
                idTokenValue = (String)requestBodyMap.get("idToken");
                //refreshTokenValue = (String)requestBodyMap.get("refreshToken");
            }
//            else if (registrationId.equals("naver")){
//
//            }


        } catch (IOException e) {
            throw new RuntimeException("Failed to extract payload of HttpServletRequest. getRequestBody() ", e);
        } catch (Exception e){
            logger.error(e.toString());
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(idTokenValue, refreshTokenValue);

        setDetails(request, authToken);

        return this.getAuthenticationManager().authenticate(authToken);

    }



}
