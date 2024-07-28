package com.tft.potato.rest.user.entity;

import com.tft.potato.common.entity.CommonEntity;
import com.tft.potato.config.security.dto.OAuthAttributes;
import com.tft.potato.rest.user.dto.UserRole;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@EqualsAndHashCode
@Table(name = "\"user\"")
public class User extends CommonEntity {

    @Id
    @GenericGenerator(
            name = "UserIdGenerator",
            // 전략을 정의한 클래스 Full Path 입력한다
            strategy = "com.tft.potato.common.generator.IdGenerator",
            // UUID란 이름으로 넘길 파라미터 값을 정한다

            parameters = @org.hibernate.annotations.Parameter(name = "userIdPrefix", value = "US")
    )
    @GeneratedValue(generator = "UserIdGenerator") // 위에서 정의한 이름
    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * 이메일
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 유저 구분 코드
     */
//    @Column(name = "user_dv_cd")
//    private String userDvCd;

    /**
     * 닉네임
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 소셜로그인구분코드
     */
    @Column(name = "social_type_cd")
    private String socialTypeCd;

    /**
     * 성별코드
     */
    @Column(name = "gender_cd")
    private String genderCd;

    /**
     * 유저 소셜 프로필 사진 링크
     */
    @Column(name = "user_profile_url")
    private String userProfileUrl;

    /**
     * 유저이름
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 생일
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 탈퇴일시
     */
    @Column(name = "left_ts")
    private Timestamp leftTs;

    /**
     * 휴면 상태
     */
    @Column(name = "is_dormant")
    private String isDormant;

    /**
     * 자기소개글
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * 관심지역코드
     */
    @Column(name = "interest_city_cd")
    private String interestCityCd;

    /**
     * 관심업종코드
     */
    @Column(name = "interest_busi_cd")
    private String interestBusiCd;

    @Column(name = "last_login_ts", nullable = false)
    private String lastLoginTs;

    /**
     * 유저 권한
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId + '\'' +
                "role=" + role.toString() + '\'' +
                "email=" + email + '\'' +
                "birthday=" + birthday + '\'' +
                "socialTypCd=" + socialTypeCd + '\'' +
                "nickname=" + nickname + '\'' +
                "genderCd=" + genderCd + '\'' +
                "userProfileUrl=" + userProfileUrl + '\'' +
                "userName=" + userName + '\'' +
                "isDormant=" + isDormant + '\'' +
                "introduction=" + introduction + '\'' +
                "interestCityCd=" + interestCityCd + '\'' +
                "interestBusiCd=" + interestBusiCd + '\'' +
                "lastLoginTs=" + lastLoginTs + '\'' +
                '}';
    }

    @Builder
    public User(String email, String socialTypeCd, String userProfileUrl
            , UserRole role, String nickname, String genderCd, String userName, String birthday, String isDormant, String lastLoginTs){

        this.email = email ;
        this.socialTypeCd = socialTypeCd ;
        this.userProfileUrl = userProfileUrl ;
        this.role = role ;
        this.nickname = nickname ;
        this.genderCd = genderCd ;
        this.userName = userName ;
        this.birthday = birthday ;
        this.isDormant = isDormant ;
        this.lastLoginTs = lastLoginTs;
    }
    // 로그인 시도한 소셜 정보가 변경되면 로그인 시점에 반영한다.
//    public User updateSocialInfo(OAuthAttributes authAttributes) throws Exception{
//        if(StringUtils.)
//    }

    //닉네임 변경
    public User changeNickname(String newNickName) {
        this.nickname = newNickName;
        return this;
    }

    public User changeLastLoginTs(){
        this.lastLoginTs = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return this;
    }

    //소개글 변경
    public User changeIntroduction(String newIntroduction) throws Exception{
        if(StringUtils.isNotBlank(newIntroduction)){
            this.introduction = newIntroduction;
        }else{
            throw new Exception("자기소개는 1글자 이상 입력해야합니다.");
        }

        return this;
    }

    // 유저 활성화
    public void activateUser(){
        this.isDormant = "N";
    }








}
