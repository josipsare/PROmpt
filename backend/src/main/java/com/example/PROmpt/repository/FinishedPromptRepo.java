package com.example.PROmpt.repository;

import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.User;
import com.example.PROmpt.models.UserPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedPromptRepo extends JpaRepository<FinishedPrompt,Long> {
    FinishedPrompt findByUserPrompt(UserPrompt userPrompt);
}
