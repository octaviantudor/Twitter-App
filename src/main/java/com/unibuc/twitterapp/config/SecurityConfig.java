package com.unibuc.twitterapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/posts/*").permitAll()
                .antMatchers("/users/*").permitAll()
                .antMatchers("/posts/feed").hasRole("USER").and()
                .formLogin().loginPage("/show-login")
                .loginProcessingUrl("/authUser")
                .failureUrl("/login-error").permitAll()
                .defaultSuccessUrl("/posts/feed").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");

    }


}

