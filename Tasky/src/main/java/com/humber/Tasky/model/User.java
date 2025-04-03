package com.humber.Tasky.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    
    private String avatarUrl;
    private List<String> invitations = new ArrayList<>();
    
    @DBRef(lazy = true)
    private Set<Task> tasks = new HashSet<>();

    public User() {}
    
    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public List<String> getInvitations() { return invitations; }
    public Set<Task> getTasks() { return tasks; }

    // Business methods
    public void addInvitation(String inviterId) {
        if (!invitations.contains(inviterId)) {
            invitations.add(inviterId);
        }
    }

    public void removeInvitation(String inviterId) {
        invitations.remove(inviterId);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}