package com.tft.potato.rest.welcome;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRestController {

    @GetMapping("health")
    public String checkHealth(){
        return "Potato server is running.";
    }

}
