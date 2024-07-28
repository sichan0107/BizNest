package com.tft.potato.rest.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class LoginResponseDto {
    private String successLogin;
    private String userId;
    //private String userDvCd;
    private String email;
    private String socialProvider;
    private String nickname;
    private String genderCd;
    private String userProfileUrl;
    private String userName;
    private String birthday;
    private String joinDtm; // 쿼리에서 Timestamp createdTs 이거 YYYYMMDD로 변경
    private String isDormant; // 휴면상태
    private String introduction; // 자기소개글
    private String interestCityCd;
    private String interestBusiCd;
    private String role;
    private String accessToken;
    private String refreshToken;



}
