package com.tft.potato.config.security;

import com.tft.potato.config.SkipPathList;
import com.tft.potato.config.security.filter.JwtAuthenticationFilter;
import com.tft.potato.config.security.handler.CustomLogoutSuccessHandler;
import com.tft.potato.config.security.handler.SuccessLoginHandler;
import com.tft.potato.config.security.provider.JwtProvider;
import com.tft.potato.config.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final SuccessLoginHandler successLoginHandler;
    //private final FailLoginHandler failLoginHandler;


    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception{
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(new JwtProvider());
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] permitPaths = SkipPathList.getPermitAllPaths().toArray(new String[0]);
        log.info("===> Auth Permit Paths : " + Arrays.deepToString(permitPaths));

        http
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(permitPaths).permitAll()
                //.antMatchers("/greeting").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterAfter(getJwtAuthenticationFilter(), WebAsyncManagerIntegrationFilter.class)

                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies()
                .clearAuthentication(true)
                .logoutSuccessHandler(customLogoutSuccessHandler())

                .and()
                .oauth2Login()
                .successHandler(successLoginHandler)
                //.failureHandler(failLoginHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService)

        ;

        return http.build();
    }

    /*
        <커스텀된 필터들을 추가하는 영역>
        Security filter chain: [
              DisableEncodeUrlFilter
              WebAsyncManagerIntegrationFilter
              JwtAuthenticationFilter ->
              SecurityContextPersistenceFilter -> 각 요청마다 Context를 다시 만들기 때문에 JWT 검증을 이거 이전에 한다.
              HeaderWriterFilter
              LogoutFilter
              OAuth2AuthorizationRequestRedirectFilter
              OAuth2LoginAuthenticationFilter -> 인증 전에 처리가 필요하면 이거 전에 추가한다.
              DefaultLoginPageGeneratingFilter
              DefaultLogoutPageGeneratingFilter
              RequestCacheAwareFilter
              SecurityContextHolderAwareRequestFilter
              AnonymousAuthenticationFilter
              SessionManagementFilter
              ExceptionTranslationFilter
              FilterSecurityInterceptor -> 로깅이나 모니터링용 필터는 이거 뒤에 추가한다
        ]
     */


}
