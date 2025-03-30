package com.humber.Tasky.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Model Class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String teams;

    //constructor without id parameter
    public Team(String teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        //Returns the presentation of the task
        return "Task(" +
                "id=" + id +
                ", teams='" + teams + '\'' +
                '}';
    }
}
