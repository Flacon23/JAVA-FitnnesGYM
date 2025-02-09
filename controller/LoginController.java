/** Clasa pentru gestionrea securitatii aplicatiei
 * @author Rau Flavius
 * @version  23 Decembrie 2024
 */
package com.fitnessapp.controller;

import com.fitnessapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        List<String> passwordErrors = loginService.validatePassword(password);

        if (!passwordErrors.isEmpty()) {
            model.addAttribute("error", String.join("<br>", passwordErrors));
            return "login";
        }

        boolean authenticated = loginService.authenticate(username, password);

        if (authenticated) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Utilizator sau parolă incorecte!");
            return "login";
        }
    }
}
