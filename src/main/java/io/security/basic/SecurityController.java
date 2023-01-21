package io.security.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {
    //TODO : 왜 로그인 처리 요청이 여기로 안 들어오지..
    // => form login API 사용할 때, .loginPage() 이후에 .loginProcessUrl() 경로로 post 요청해야 함!!!
    // get 요청해서 security filter chain이 동작안했었음..
    // form 제출할 때 form action 안쓰고 따로 이벤트만들어서 하려고 했는데... 안되네.. 걍 form action 쓰니까 해결됨..
    @PostMapping("/processLogin")
    public ModelAndView processLogin(@RequestParam String username, @RequestParam String password) {
        ModelAndView mav = new ModelAndView("success");
        mav.addObject("username", username);
        return mav;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/admin")
    public String adminTest(){
        return "success";
    }
}
