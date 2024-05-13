package com.example.PROmpt.service;


import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Object getUsers(Long userId);
}
