package com.example.spring.crud.phrase.config;

import com.example.spring.crud.phrase.rest.JwtAuthenticationTokenFilter;
import com.example.spring.crud.phrase.rest.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    public RestAuthenticationEntryPoint restServiceEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/**").permitAll();
//        http.authorizeRequests().antMatchers("/**").permitAll().and().httpBasic();
//        http.csrf().ignoringAntMatchers("/api/v1/**");
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
        http.antMatcher("/api/v1/**").httpBasic()
                .authenticationEntryPoint(restServiceEntryPoint())
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

//        super.configure(http);

    }
}
