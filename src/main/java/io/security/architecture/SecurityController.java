package io.security.architecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping(value = "/ch2")
@RestController("SecurityController2")
public class SecurityController {

    @GetMapping
    public String index(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authenticationInSession = securityContext.getAuthentication();

        log.info("authentication from securityContext = " + authentication.toString());
        log.info("authentication from session = " + authenticationInSession.toString());

        // session.invalidate();
        return "index";
    }

    @GetMapping("/thread")
    public String thread() {
        Thread thread = new Thread(
                () -> {
                    // 기본 전략인 스레드별 thread local을 가지는 방식을 쓰면 메인 스레드와 다른 스레드 로컬을 써서 null이 나오게 됨!
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    log.info("authentication in different thread = " + authentication);

                }
        );

        thread.start();

        return "thread";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
