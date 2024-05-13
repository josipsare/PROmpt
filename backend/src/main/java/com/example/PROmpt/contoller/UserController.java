package com.example.PROmpt.contoller;


import com.example.PROmpt.service.UserPromptService;
import com.example.PROmpt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends AplicationController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) Long userId){
        return new ResponseEntity<>(userService.getUsers(userId), HttpStatus.OK);
    }
}
