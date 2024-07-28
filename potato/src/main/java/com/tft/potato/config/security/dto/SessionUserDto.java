package com.tft.potato.config.security.dto;

import com.tft.potato.rest.user.dto.UserRole;
import com.tft.potato.rest.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 *  인증된 사용자만 담아서 세션에 저장하는 DTO
 */
@Getter
public class SessionUserDto implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String picture;
    private String roleKey;

    //===== 그외에 추가해봄직한 값들
    // private String loginTime;

    public SessionUserDto(User user){
        this.userId = user.getUserId();
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.picture = user.getUserProfileUrl();
        this.roleKey = user.getRole().getKey();
    }


}
