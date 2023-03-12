package io.security.project.security.admin;

import io.micrometer.common.util.StringUtils;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
public class AdminAuthenticationProvider implements AuthenticationProvider {

	@Override
	public boolean supports(Class<?> clazz) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(clazz);
	}

	private static String adminKey = "1234";

	@Override
	public Authentication authenticate(Authentication authentication) {
		String adminId = (String) authentication.getPrincipal();

		if (StringUtils.isBlank(adminId) || !adminKey.equals(authentication.getCredentials().toString())) {
			return null;
		}

		var ret = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null,
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		ret.setDetails(authentication.getDetails());

		return ret;
	}
}
