package kr.or.connect.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.or.connect.reservation.domain.user.User;
import kr.or.connect.reservation.dto.RegistrationForm;
import kr.or.connect.reservation.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("form") RegistrationForm form) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") RegistrationForm form) {
        
        User user = new User(form.getEmail(), form.getPassword(), form.getName(), form.getPhone());
        userService.createUser(user);

        return "redirect:/login";
    }
}
