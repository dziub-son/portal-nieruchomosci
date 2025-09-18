package pl.twojafirma.nieruchomosci.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.twojafirma.nieruchomosci.dto.UserRegistrationDto;
import pl.twojafirma.nieruchomosci.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
                               BindingResult result,
                               Model model) {
        if (userService.findByUsername(userDto.getUsername()) != null) {
            result.rejectValue("username", null, "Użytkownik o tej nazwie już istnieje.");
        }
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        logger.info("Zarejestrowano nowego użytkownika: {}", userDto.getUsername());
        return "redirect:/login?register_success";
    }
}