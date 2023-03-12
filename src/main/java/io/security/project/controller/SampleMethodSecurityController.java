package io.security.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RestController
public class SampleMethodSecurityController {

	@GetMapping("/health-check")
	public String healthCheck(){
		return "ok";
	}
}
