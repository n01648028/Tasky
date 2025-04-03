package com.humber.Tasky.controller;

import com.humber.Tasky.model.User;
import com.humber.Tasky.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class WebController {

    private final AuthService authService;

    @Autowired
    public WebController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            return "redirect:/calendar";
        }
        model.addAttribute("features", List.of(
            "Organize your tasks efficiently",
            "Share tasks with team members",
            "Calendar view for better planning",
            "Priority-based task management"
        ));
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(@AuthenticationPrincipal User user, Model model, CsrfToken csrfToken) {
        if (user != null) {
            return "redirect:/calendar";
        }
        // Add CSRF token to model if it exists
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "login";
    }


    // @PostMapping("/login")
    // public String processLogin(@RequestParam String email,
    //                          @RequestParam String password,
    //                          Model model) {
    //     // Authentication is handled by Spring Security
    //     return "redirect:/calendar";
    // }

    @PostMapping("/login")
public String processLogin() {
    // Let Spring Security handle the authentication
    return "redirect:/calendar";
}

    @GetMapping("/register")
    public String showRegisterPage(@AuthenticationPrincipal User user, Model model, CsrfToken csrfToken) {
        if (user != null) {
            return "redirect:/calendar";
        }
        model.addAttribute("user", new User());
        // Add CSRF token to model if it exists
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user, 
                                    Model model, 
                                    RedirectAttributes redirectAttributes) {
        try {
            authService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }

    @GetMapping("/calendar")
    public String calendarView(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "calendar";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/tasks/new")
public String showTaskForm(@AuthenticationPrincipal User user, Model model) {
    if (user == null) {
        return "redirect:/login";
    }
    return "task-form";
}
}