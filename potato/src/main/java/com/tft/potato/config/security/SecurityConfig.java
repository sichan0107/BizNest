package com.tft.potato.config.security;

import com.tft.potato.config.SkipPathList;
import com.tft.potato.config.security.handler.CustomLogoutSuccessHandler;
import com.tft.potato.config.security.handler.FailLoginHandler;
import com.tft.potato.config.security.handler.SuccessLoginHandler;
import com.tft.potato.config.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.List;
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private final CustomOAuth2UserService customOAuth2UserService;
   private final SuccessLoginHandler successLoginHandler;
   //private final FailLoginHandler failLoginHandler;


   @Bean
   public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
       return new CustomLogoutSuccessHandler();
   }


    @Override
    protected void configure (HttpSecurity http) throws Exception{
        List<String> skipPathList = new SkipPathList();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(skipPathList.toArray(new String[0])).permitAll()
                .antMatchers("/user/greeting").permitAll()
                .anyRequest().authenticated()

                .and()
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

    }

//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider(){
//        return new CustomAuthenticationProvider();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(customAuthenticationProvider());
//    }


//    @Override
//    protected void configure (HttpSecurity http) throws Exception{
//        List<String> skipPathList = new SkipPathList();
//
//        http
//                .csrf().disable()
//                .formLogin().disable()
//                .rememberMe().disable()
//                .httpBasic().disable()
//                .authorizeRequests()
//                .antMatchers(skipPathList.toArray(new String[0])).permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .deleteCookies()
//                .clearAuthentication(true)
//                .logoutSuccessHandler(customLogoutSuccessHandler())
//        ;
//
//        http.addFilterAt(getCustomTokenValidationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }



//    @Bean
//    public CustomTokenValidationFilter getCustomTokenValidationFilter() throws Exception{
//        CustomTokenValidationFilter filter = new CustomTokenValidationFilter();
//        filter.setAuthenticationManager(getAuthenticationManager());
//        filter.setFilterProcessesUrl("/login");
//        filter.setAuthenticationSuccessHandler(appAuthenticationSuccessHandler());
//        filter.setAuthenticationFailureHandler(appAuthenticationFailureHandler());
//        filter.setAllowSessionCreation(true);
//
//        return filter;
//    }
//
//
//    @Bean
//    @Primary
//    protected AuthenticationManager getAuthenticationManager() throws Exception{
//        return super.authenticationManager();
//    }
//


}
