package com.tft.potato.config.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GoogleIdTokenDto {
    private String iss; // https://accounts.google.com
    private String azp;
    private String aud;
    private String sub; // 유저 고유 코드
    private String hd; // 이메일 도메인
    private String email;
    private String email_verified;
    private String at_hash;
    private String nonce;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String iat;
    private String exp;
}
