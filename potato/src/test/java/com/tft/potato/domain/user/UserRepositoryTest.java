package com.tft.potato.domain.user;


import com.tft.potato.rest.user.dto.UserRole;
import com.tft.potato.rest.user.entity.User;
import com.tft.potato.rest.user.repo.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

//    @After
//    public void deleteUser(String userId){
//
//        userRepository.deleteUserByUserId(userId);
//    }

    // user 엔티티 생성시 ID가 어떻게 생성되는지 확인용
//    @Test
//    public void saveUser(){
//        User user = User.builder()
//                        .oauthId("103937684561128944189")
//                        .email("mg.kim@autowini.com")
//                        .socialTypeCd("ggl")
//                        .userProfileUrl("https://lh3.googleusercontent.com/a/ACg8ocI12MZNODuCnjH_livF6qxs2swEmB_nS0cRqGPdoTfeu8Ca7A=s96-c")
//                        .role(UserRole.USER)
//                        .nickname("뭉껍")
//                        .genderCd("M")
//                        .userName("뚱기")
//                        .birthday("19950107")
//                        .isDormant("Y")
//                        .build();
//
//        userRepository.saveUser(user);
//
//        User newUser = userRepository.findByOauthId("103937684561128944189");
//        System.out.println("User : " + newUser.toString());
//        Assert.assertEquals("103937684561128944189", newUser.getOauthId());
//
//    }

}
