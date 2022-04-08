package com.example.spring.security.jwt.config;

import com.example.spring.security.jwt.rest.CustomAccessDeniedHandler;
import com.example.spring.security.jwt.rest.JwtAuthenticationTokenFilter;
import com.example.spring.security.jwt.rest.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable crsf cho đường dẫn /rest/**
        http.csrf().ignoringAntMatchers("/api/v1/**");

        http.authorizeRequests().antMatchers("/api/v1/auth/login**").permitAll();
        http.antMatcher("/api/v1/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.POST, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());


//        //Disable crsf url /rest/**
//        http.csrf().ignoringAntMatchers("/api/v1/**");
////        http.csrf().ignoringAntMatchers("/rest/api/**");
//        http.authorizeRequests().antMatchers("/api/v1/auth/login").permitAll();
//        http.antMatcher("/api/v1/**").httpBasic()
//                .authenticationEntryPoint(restServicesEntryPoint())
////                .and()
////                .authorizeRequests()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
////                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());



//        http.cors()
//                .and()
//                .csrf().disable().exceptionHandling().authenticationEntryPoint(restServicesEntryPoint()) //Ném ngoại lệ
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().antMatchers().permitAll().antMatchers("/api/v1/auth/login") //Đường dẫn /api/login-admin sẽ được truy cập bình thường mà ko cần check
//                .permitAll()
//                .anyRequest()
//                .authenticated(); //Mọi đường dẫn còn lại yêu cầu gửi Authentication String trên header để check.

    }


}
