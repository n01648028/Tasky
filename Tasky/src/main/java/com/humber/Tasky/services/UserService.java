package com.humber.Tasky.services;

import com.humber.Tasky.models.MyUser;
import com.humber.Tasky.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //save the user to the database(register)
    //0 - user already exists, 1 - user saved successfully
    public int saveUser(MyUser user) {
        //if the user already exists
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return 0;
        }
        //encrypting the password before the save
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //save the user
        userRepository.save(user);
        return 1;
    }
}
