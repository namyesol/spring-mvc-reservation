package kr.or.connect.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.or.connect.reservation.dto.LoginForm;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginForm(@ModelAttribute("form") LoginForm form) {
        return "login";
    }
}
