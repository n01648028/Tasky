package com.humber.Tasky.controllers;

import com.humber.Tasky.models.User;
import com.humber.Tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @PutMapping("/me")
    public User updateCurrentUser(@RequestBody User userDetails, @AuthenticationPrincipal User user) {
        return userService.updateUser(user.getId(), userDetails);
    }

    @PostMapping("/invite")
    public void sendInvitation(
            @RequestParam String recipientEmail,
            @AuthenticationPrincipal User user) {
        userService.sendInvitation(user.getId(), recipientEmail);
    }

    @PostMapping("/invitations/{inviterId}/accept")
    public void acceptInvitation(
            @PathVariable String inviterId,
            @AuthenticationPrincipal User user) {
        userService.acceptInvitation(user.getId(), inviterId);
    }

    @PostMapping("/invitations/{inviterId}/reject")
    public void rejectInvitation(
            @PathVariable String inviterId,
            @AuthenticationPrincipal User user) {
        userService.rejectInvitation(user.getId(), inviterId);
    }
}