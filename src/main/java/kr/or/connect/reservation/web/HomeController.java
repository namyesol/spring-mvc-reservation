package kr.or.connect.reservation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.connect.reservation.domain.user.User;
import kr.or.connect.reservation.security.CurrentUser;

@Controller
public class HomeController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/protected") 
    public String protectedResource(@CurrentUser User user, Model model) {
        log.debug("user={}", user);
        model.addAttribute("user", user);
        return "resources/protected";
    }
}
