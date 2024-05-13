package com.example.PROmpt.service;


import com.example.PROmpt.DTO.GradeDTO;
import com.example.PROmpt.DTO.ResponseDTO;
import com.example.PROmpt.DTO.ResponseDTOR;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseService {

    Object test();

    Object save(ResponseDTO prompt);

    List<ResponseDTOR> responsesByLLM(Long id);

    Object getPrompts(Long id);

    Object gradeResponse(GradeDTO gradeDTO);
}
