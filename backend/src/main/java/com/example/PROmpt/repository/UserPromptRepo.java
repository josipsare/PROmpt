package com.example.PROmpt.repository;

import com.example.PROmpt.models.User;
import com.example.PROmpt.models.UserPrompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPromptRepo extends JpaRepository<UserPrompt,Long> {


    List<UserPrompt> findAllByUser(User user);
}
