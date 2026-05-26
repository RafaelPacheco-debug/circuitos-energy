package pt.circuitos.circuitos_energy.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.circuitos.circuitos_energy.model.RegistrationForm;
import pt.circuitos.circuitos_energy.service.AppUserService;

@Controller
public class RegistrationController {

    private final AppUserService userService;

    public RegistrationController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(
            @Valid @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "password.mismatch", "As senhas não coincidem");
        }

        if (userService.usernameExists(form.getUsername())) {
            result.rejectValue("username", "username.exists", "Nome de utilizador já existe");
        }

        if (userService.emailExists(form.getEmail())) {
            result.rejectValue("email", "email.exists", "Email já utilizado");
        }

        if (result.hasErrors()) {
            return "registro";
        }

        userService.register(
                form.getUsername(),
                form.getPassword(),
                form.getFullName(),
                form.getEmail(),
                "USER");

        redirectAttributes.addAttribute("registered", "true");
        return "redirect:/login";
    }
}
