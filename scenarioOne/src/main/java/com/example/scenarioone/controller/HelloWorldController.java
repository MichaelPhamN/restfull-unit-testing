package com.example.scenarioone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello-world")
    public ResponseEntity<String> helloworld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
