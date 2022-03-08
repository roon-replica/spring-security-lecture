package io.security.basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();

        http.formLogin()
//                .loginPage("/loginPage")
                .defaultSuccessUrl("/")
                .failureUrl("/loginPage")
                .usernameParameter("custom_username_input_name")
                .passwordParameter("custom_password_input_name")
                .loginProcessingUrl("/loginProcess")
                .successHandler((request, response, authentication) -> {
                    System.out.println("authenticate success : " + authentication.getName());
                    response.sendRedirect("/");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("authenticate failed :" + exception.getMessage());
                    response.sendRedirect("/loginPage");
                })
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> response.sendRedirect("/login"))
                .deleteCookies("remember-me");

        http.rememberMe()
                .rememberMeParameter("remember")    // default : remember-me
                .tokenValiditySeconds(30)   // set 30 seconds for test
                .userDetailsService(username -> new User("leemr", "1234", new ArrayList<GrantedAuthority>()));

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false); // true -> prevent , false(default) -> expire previous session


    }
}
