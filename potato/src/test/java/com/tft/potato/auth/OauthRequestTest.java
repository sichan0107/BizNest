package com.tft.potato.auth;

import com.tft.potato.rest.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = UserRestController.class)

//-> 모든 빈을 다로드한다. 소스가 커지면 느려진다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@ComponentScan(basePackages = "com.tft.potato.rest.user.controller")
public class OauthRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext context;

    // UserRestController 클래스에서 의존하는 애를 빈으로 넣어준다.
    @MockBean
    UserService userService;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("이메일로 User 엔티티 조회 테스트")
    public void junit5RestControllerTest() throws Exception{
        //String url = "/user/thdtl95@gmail.com";
        String url = "http://localhost:" + port + "/user/greeting";
        String response = testRestTemplate.getForObject(url, String.class);
        System.out.println("resposne : " + response);

        //Assertions.assertThat();
    }

    @AfterEach
    public void end(){
        System.out.println("=============== start test ===============");
    }

    /*
        todo : mockMvc랑 WebApplicationContext가 null로 설정되서 Test시 NPE발생
     */

    @Test
    @DisplayName("이메일로 User 엔티티 조회 테스트")
    @WithMockUser(roles = "USER") //Spring security가 있어도 테스트가 가능하게함.
    public void searchUserByEmailTest() throws Exception {
        //String url = "/user/thdtl95@gmail.com";
        String url = "http://localhost:8080/user/info";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content("thdtl95@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //result.getResponse().
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("result : " + responseBody);

    }



}
