package com.humber.Tasky.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Model Class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //All the attributes in the task
    private int id;
    private String title;
    private String assignee;
    private String priority;
    private String project;
    private String deadline;
    private String status;
    @Column(length = 2147483647)
    private String details;

    //constructor without id parameter
    public Task(String title, String assignee, String priority, String project, String deadline, String status, String details) {
        this.title = title;
        this.assignee = assignee;
        this.priority = priority;
        this.project = project;
        this.deadline = deadline;
        this.status = status;
        this.details = details;
    }

    @Override
    public String toString() {
        //Returns the presentation of the task
        return "Task(" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", assignee='" + assignee + '\'' +
                ", priority='" + priority + '\'' +
                ", project='" + project + '\'' +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
