package io.security.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SecurityController {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin/pay")
    public String pay() {
        return "admin";
    }

    @GetMapping("/admin/other")
    public String other() {
        return "admin else";
    }

    @GetMapping("/denied")
    public String denied() {
        return "access denied";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

}
