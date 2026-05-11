package pt.circuitos.circuitos_energy.controller;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pt.circuitos.circuitos_energy.model.AppUser;
import pt.circuitos.circuitos_energy.model.PerfilForm;
import pt.circuitos.circuitos_energy.service.AppUserService;

@Controller
public class ProfileController {

    private final AppUserService userService;

    public ProfileController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/perfil")
    public String perfil(Authentication authentication, Model model) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        PerfilForm form = new PerfilForm();
        form.setFullName(user.getFullName());
        form.setEmail(user.getEmail());

        model.addAttribute("perfilForm", form);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("enabled", user.isEnabled());
        return "perfil";
    }

    @PostMapping("/perfil")
    public String salvarPerfil(
            Authentication authentication,
            @Valid @ModelAttribute("perfilForm") PerfilForm form,
            BindingResult result,
            Model model) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getRoles());
            model.addAttribute("enabled", user.isEnabled());
            return "perfil";
        }

        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());

        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            user.setPassword(userService.encodePassword(form.getPassword()));
        }

        userService.save(user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("enabled", user.isEnabled());
        model.addAttribute("success", true);
        return "perfil";
    }
}
