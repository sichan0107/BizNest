package com.tft.potato.rest.user.controller;

import com.tft.potato.rest.user.entity.User;
import com.tft.potato.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class UserRestController {

    private final UserService userService;

    @GetMapping("/user/info")
    public ResponseEntity<User> searchUser(@RequestParam("email") String email){
        User user = userService.getUserByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> searchAllUser(){
        List<User> userList = userService.getAllUser();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


//    @PostMapping("/info")
//    public ResponseEntity<User> searchUser(@RequestBody String email){
//        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
//    }


}
