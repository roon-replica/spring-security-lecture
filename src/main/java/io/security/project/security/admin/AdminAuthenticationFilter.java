package io.security.project.security.admin;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class AdminAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter implements Ordered {

	public static final String ADMIN_PRINCIPAL_HEADER = "X-ADMIN-REQUESTER-ID";
	public static final String ADMIN_CREDENTIAL_HEADER = "X-ADMIN-SECRET";

	private int order;

	public AdminAuthenticationFilter(AdminAuthenticationProvider adminAuthenticationProvider, int order) {
		super.setCheckForPrincipalChanges(true);
		super.setAuthenticationManager(new ProviderManager(Collections.singletonList(adminAuthenticationProvider)));
		this.order = order;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader(ADMIN_PRINCIPAL_HEADER);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return request.getHeader(ADMIN_CREDENTIAL_HEADER);
	}

	@Override
	public int getOrder() {
		return order;
	}
}
