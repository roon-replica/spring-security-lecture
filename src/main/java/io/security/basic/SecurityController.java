package io.security.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {
    @GetMapping("/processLogin")
    public ModelAndView processLogin(String username, String password) {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("username", username);
        return mav;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
