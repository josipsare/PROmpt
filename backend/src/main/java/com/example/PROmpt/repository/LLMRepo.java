package com.example.PROmpt.repository;

import com.example.PROmpt.models.LLM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LLMRepo extends JpaRepository<LLM,Long> {
    Object getAllById(Long id);
}
