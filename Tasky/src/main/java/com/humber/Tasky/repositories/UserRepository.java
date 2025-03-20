package com.humber.Tasky.repositories;

import com.humber.Tasky.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    //find the user by the username
    public Optional<MyUser> findByUsername(String username);
}
