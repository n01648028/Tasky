package com.humber.Tasky.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    //constructor without id parameter
    public Task(String title, String assignee, String priority, String project, String deadline, String status) {
        this.title = title;
        this.assignee = assignee;
        this.priority = priority;
        this.project = project;
        this.deadline = deadline;
        this.status = status;
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
                '}';
    }
}
