package com.example.PROmpt.service.impl;


import com.example.PROmpt.DTO.GradeDTO;
import com.example.PROmpt.DTO.ResponseDTO;
import com.example.PROmpt.DTO.ResponseDTOR;
import com.example.PROmpt.RecordNotFoundException;
import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.Response;
import com.example.PROmpt.models.User;
import com.example.PROmpt.models.UserPrompt;
import com.example.PROmpt.repository.LLMRepo;
import com.example.PROmpt.repository.ResponseRepo;
import com.example.PROmpt.repository.UserPromptRepo;
import com.example.PROmpt.repository.UserRepo;
import com.example.PROmpt.service.ResponseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    private final LLMRepo llmRepo;

    private final UserRepo userRepo;

    private final UserPromptServiceImpl userPromptService;
    private final ResponseRepo responseRepo;
    private final UserPromptRepo userPromptRepo;

    public ResponseServiceImpl(LLMRepo llmRepo, UserRepo userRepo, UserPromptServiceImpl userPromptService, ResponseRepo responseRepo, UserPromptRepo userPromptRepo) {
        this.llmRepo = llmRepo;
        this.userRepo = userRepo;
        this.userPromptService = userPromptService;
        this.responseRepo = responseRepo;
        this.userPromptRepo = userPromptRepo;
    }

    @Override
    public Object test() {
        return "testttttt";
    }

    public List<ResponseDTOR> responsesByLLM(Long id){
        List<Response> list = responseRepo.getAllByLLMId(id);
        List<ResponseDTOR> listDTOR = new ArrayList<>();
        for(Response response : list){
            ResponseDTOR responseDTOR = new ResponseDTOR(response.getFinishedPrompt().getText(),response.getText(),response.getUserPrompt().getPrompt());
            listDTOR.add(responseDTOR);
        }
        return listDTOR;
    }

    @Override
    public Object getPrompts(Long userId) {

        if (userId==null){
            return responseRepo.findAll();
        }

        if (userRepo.findById(userId).isEmpty()){
            throw new RecordNotFoundException("User with provided ID doesn't exist");
        }

        User user = userRepo.findById(userId).get();

        List<Response> responses = new ArrayList<>();
        List<UserPrompt> userPrompts = (List<UserPrompt>) userPromptService.getPrompts(userId);
        for(UserPrompt userPrompt : userPrompts){
            responses.add(responseRepo.findByUserPrompt(userPrompt));
        }

        return responses;
    }

    @Override
    public Object gradeResponse(GradeDTO gradeDTO) {
        System.out.println("ovo je tekst sa kojim trazimo sto zelimo promjeniti");
        System.out.println(gradeDTO.getResponseReview());
        Response response = responseRepo.findAll().getLast();
        System.out.println("ovo je response koji treba promjeniti");
        System.out.println(response.getText());
        response.setGrade(gradeDTO.getReview());
        return responseRepo.save(response);
    }

    //todo treba prominiti vjetojatno
    @Override
    public Object save(ResponseDTO responseDTO) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(responseDTO.getUserPromptID()+"---------------------------");
        System.out.println(responseDTO.getLlmID()+"---------");
//        Response response = new Response(
//                responseDTO.getText(),
//                responseDTO.getGrade(),
//                llmRepo.findById(responseDTO.getLlmID()).get(),
//                userPromptRepo.findById(responseDTO.getUserPromptID()).get()
//                );
        return null;
    }

}
