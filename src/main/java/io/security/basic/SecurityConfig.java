package io.security.basic;

import io.security.architecture.MultiSecurityConfig;
import io.security.architecture.MultiSecurityConfig2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Import({MultiSecurityConfig.class, MultiSecurityConfig2.class})
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/", "/login").permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/success")
//                .defaultSuccessUrl("/")
                .failureUrl("/fail")
//                .usernameParameter("custom_username_input_name")
//                .passwordParameter("custom_password_input_name")
                .loginProcessingUrl("/processLogin")
                .permitAll();

//                .successHandler((request, response, authentication) -> {
//                    System.out.println("authenticate success : " + authentication.getName());
//                    response.sendRedirect("/");
//                })

//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        RequestCache requestCache = new HttpSessionRequestCache();
//                        SavedRequest savedRequest = requestCache.getRequest(request, response);
//                        String redirectUrl = savedRequest.getRedirectUrl();
//                        response.sendRedirect(redirectUrl);
//                    }
//                })
//                .failureHandler((request, response, exception) -> {
//                    System.out.println("authenticate failed :" + exception.getMessage());
//                    response.sendRedirect("/login");
//                })
//                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> response.sendRedirect("/login"))
                .deleteCookies("remember-me");

//        http.rememberMe()
//                .rememberMeParameter("remember")    // default : remember-me
//                .tokenValiditySeconds(30)   // set 30 seconds for test
//                .userDetailsService(username -> new User("leemr", "1234", new ArrayList<GrantedAuthority>()));

//        http.sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false); // true -> prevent , false(default) -> expire previous session
//
//        http.sessionManagement()
//                .sessionFixation().changeSessionId();   //default
////                .sessionFixation().none();      // none으로 설정하면 session fixation protection 없어서 따로 보호 제공해야 함

        http.authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {  // 인증 실패 시
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {      // 인가 실패 시
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.sendRedirect("/denied");
                    }
                });

//        http.csrf().disable();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("1234")).roles("USER");

        auth.inMemoryAuthentication()
                .withUser("sys").password(passwordEncoder().encode("1234")).roles("SYS", "USER");

        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN", "SYS", "USER");
    }
}
