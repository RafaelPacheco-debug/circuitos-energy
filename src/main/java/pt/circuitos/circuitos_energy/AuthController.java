package pt.circuitos.circuitos_energy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/recuperar-password")
    public String recuperarPassword() {
        return "recuperar-password";
    }
}
