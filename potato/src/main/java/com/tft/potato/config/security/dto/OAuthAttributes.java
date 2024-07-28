package com.tft.potato.config.security.dto;

import com.tft.potato.rest.user.dto.UserRole;
import com.tft.potato.rest.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.type.AnyType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @return : 각 소셜로그인에 따른 Oauth 정보
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String registrationId;
    private String userId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email
                                                    , String picture ,String registrationId ){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.registrationId = registrationId;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes){
        if(registrationId.equals("naver")){
            return ofNaver("id", attributes);
        }
        return ofGoogle(usernameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    /*
        Naver 로그인 응답 response -> attributes의 추가적인 필드
        1. age
        2. gender
        3. id
        4. name
        5. birthday
     */
    private static OAuthAttributes ofNaver(String usernameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }


    public User toUser(){
        return User.builder()
                //.oauthId(attributes.)
                .email(email)
                .userProfileUrl(picture)
                .userName(name)
                .isDormant("Y")
                .lastLoginTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .role(UserRole.USER)
//                .socialTypeCd("ggl")
//                .userDvCd("user")
//                .nickname("뭉껍")
//                .genderCd("M")
//                .birthday("19950107")
                .build();
    }




}
