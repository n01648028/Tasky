package com.humber.Tasky.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String fullName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String avatarUrl;

    private final List<String> invitations = new ArrayList<>();

    @DBRef
    private Set<Task> tasks = new HashSet<>();

    // Default constructor
    public User() {
    }

    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'fullName'
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for 'password'
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for 'avatarUrl'
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    // Getter for 'invitations' (no setter as it's a final list)
    public List<String> getInvitations() {
        return invitations;
    }

    // Getter and Setter for 'tasks'
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    // Method to add an invitation
    public void addInvitation(String inviterId) {
        if (invitations.size() >= 100) {
            throw new IllegalStateException("Invitation limit reached (100 max)");
        }
        invitations.add(inviterId);
    }

    // Method to remove an invitation
    public void removeInvitation(String inviterId) {
        invitations.remove(inviterId);
    }

    // Method to add a task
    public void addTask(Task task) {
        tasks.add(task);
    }
}