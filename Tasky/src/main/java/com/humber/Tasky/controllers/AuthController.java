package com.humber.Tasky.controllers;

import com.humber.Tasky.models.MyUser;
import com.humber.Tasky.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController implements ErrorController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Value("${restaurant.name}")
    private String name;

    //custom error endpoint
    @GetMapping("/error")
    public String handleError() {
        return "auth/error";
    }
    //custom login endpoint
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String message) {
        //add information to display in login like the message and name
        model.addAttribute("message", message);
        model.addAttribute("restaurantName", name);
        return "auth/login";
    }
    
    //custom logout endpoint
    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
        //perform the logout logic
        new SecurityContextLogoutHandler().logout(req, res, authentication);
        return "redirect:/login?message=You have been logged out sucessfully";
    }
    //custom registration - open the registration form
    @GetMapping("/register")
    public String register(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        model.addAttribute("user", new MyUser());
        return "auth/register";
    }
    //custom registration - save the user
    @PostMapping("/register")
    public String register(@ModelAttribute MyUser user) {
        //save the user
        int statusCode = userService.saveUser(user);
        if (statusCode == 0) {
            return "redirect:/register?message=Username already exists";
        }
        return "redirect:/login?message=Registration successful! Please login to continue";
    }
}
