package com.humber.Tasky.controllers;

import com.humber.Tasky.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import com.humber.Tasky.models.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class WebController {

    @Autowired
    private AuthController authController;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            return "redirect:/calendar";
        }
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "redirect:/calendar";
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            ResponseEntity<?> response = authController.registerUser(user);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/login?message=Registration successful! Please login.";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
        return "register";
    }

    @GetMapping("/tasks/new")
    public String showNewTaskForm(Model model) {
        return "task-form";
    }

    @GetMapping("/calendar")
    public String calendarView(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("month", java.time.LocalDate.now().getMonth().toString());
        model.addAttribute("year", java.time.LocalDate.now().getYear());
        model.addAttribute("tasks", new ArrayList<>()); // Placeholder for tasks
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

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User updatedUser, @AuthenticationPrincipal User currentUser, Model model) {
        // Update only allowed fields
        currentUser.setFullName(updatedUser.getFullName());
        currentUser.setAvatarUrl(updatedUser.getAvatarUrl());
        // Save updated user
        // userService.save(currentUser);
        return "redirect:/profile";
    }
}