package com.tft.potato.rest.user.controller;

import com.tft.potato.rest.user.entity.User;
import com.tft.potato.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("user")
@RequiredArgsConstructor

public class UserRestController {

    private final UserService userService;

    @GetMapping("info")
    public ResponseEntity<User> searchUser(@RequestParam("email") String email){
        User user = userService.getUserByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping("/info")
//    public ResponseEntity<User> searchUser(@RequestBody String email){
//        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
//    }

    @GetMapping("/health")
    public String greeting(){
        return "hello";
    }
}
