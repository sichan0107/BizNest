package com.tft.potato.rest.user.service;

import com.tft.potato.rest.user.entity.User;
import com.tft.potato.rest.user.repo.UserJpaRepository;
import com.tft.potato.rest.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;

    public User getUserInfoByUserId(String userId){
        return userRepository.findByUserId(userId);
    }

    public List<User> getAllUser(){
        return userJpaRepository.findAll();
    }


    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public String getUserIdByEmail(String email){
        return userJpaRepository.findUserIdByEmail(email);
    }

//    public void registUser(User newUser){
//        userRepository.saveUser(newUser);
//    }


}
