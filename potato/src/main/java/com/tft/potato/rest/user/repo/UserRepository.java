package com.tft.potato.rest.user.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tft.potato.rest.user.entity.QUser;
import com.tft.potato.rest.user.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Map;

@Repository
public class UserRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public UserRepository(JPAQueryFactory jpaQueryFactory, EntityManager entityManager){
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    private final QUser user = QUser.user;


    public User findByUserId(String userId){
        return jpaQueryFactory
                    .select(user)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }

    public User findByEmail(String email){
        return jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();
    }


//    public void saveUser(User user){
//        entityManager
//                .createNativeQuery("INSERT INTO USER" +
//                        "(USER_ID, OAUTH_ID, EMAIL, USER_DV_CD, NICKNAME, SOCIAL_TYPE_CD, GENDER_CD, " +
//                        "USER_PROFILE_URL, USER_NAME, BIRTHDAY, LEFT_TS, IS_DORMANT, INTRODUCTION, INTEREST_CITY_CD" +
//                        "INTEREST_BUSI_CD )" +
//                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
//                .setParameter(1, user.getUserId())
//                .setParameter(2, user.getOauthId())
//                .setParameter(3, user.getEmail())
//                .setParameter(4, user.getRole())
//                .setParameter(5, user.getNickname())
//                .setParameter(6, user.getSocialTypeCd())
//                .setParameter(7, user.getGenderCd())
//                .setParameter(8, user.getUserProfileUrl())
//                .setParameter(9, user.getUserName())
//                .setParameter(10, user.getBirthday())
//                .setParameter(11, user.getLeftTs())
//                .setParameter(12, user.getIsDormant())
//                .setParameter(13, user.getIntroduction())
//                .setParameter(14, user.getInterestCityCd())
//                .setParameter(15, user.getInterestBusiCd())
//                .executeUpdate();
//    }



    public void deleteUserByUserId(String userId){
        jpaQueryFactory
                .delete(user).where(user.userId.eq(userId))
                .execute();
    }

}
