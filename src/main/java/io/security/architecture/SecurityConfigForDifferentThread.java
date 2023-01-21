package io.security.architecture;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

@Order(1)
@Configuration
public class SecurityConfigForDifferentThread extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();

//        String strategy = SecurityContextHolder.MODE_THREADLOCAL;
        String strategy = SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;
        SecurityContextHolder.setStrategyName(strategy);
    }
}
