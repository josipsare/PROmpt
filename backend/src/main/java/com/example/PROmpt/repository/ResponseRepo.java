package com.example.PROmpt.repository;

import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.Response;
import com.example.PROmpt.models.UserPrompt;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepo extends JpaRepository<Response,Long> {

    @Query(value = "select * from response r where  r.llm_id= :llm_id ", nativeQuery = true)
    List<Response> getAllByLLMId(@Param("llm_id") Long llm_id);

    Response findByUserPrompt(UserPrompt userPrompt);

    @Query(value = "SELECT * FROM response r WHERE r.text = :text", nativeQuery = true)
    List<Response> findByText(@Param("text") String text);
}
