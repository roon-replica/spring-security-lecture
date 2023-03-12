package io.security.project.security;

import io.security.project.security.admin.AdminAuthenticationFilter;
import io.security.project.security.admin.AdminAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@EnableMethodSecurity
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.headers().frameOptions().disable().and()
				.authorizeRequests().requestMatchers("/").permitAll().and()
//				.authorizeHttpRequests().requestMatchers("/").permitAll().and()
				.exceptionHandling().authenticationEntryPoint(http403ForbiddenEntryPoint());

		return http.build();
	}

	@Bean
	public AdminAuthenticationProvider adminAuthenticationProvider() {
		return new AdminAuthenticationProvider();
	}

	@Bean
	public AdminAuthenticationFilter adminAuthenticationFilter() {
		return new AdminAuthenticationFilter(adminAuthenticationProvider(), -1);
	}

	@Bean
	public AuthenticationEntryPoint http403ForbiddenEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}
