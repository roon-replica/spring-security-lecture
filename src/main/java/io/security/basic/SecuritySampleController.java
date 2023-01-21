package io.security.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sample")
@RestController
public class SecuritySampleController {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @PostMapping("/")
    public String home() {
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
