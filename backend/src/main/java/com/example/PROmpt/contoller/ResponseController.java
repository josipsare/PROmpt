package com.example.PROmpt.contoller;

import com.example.PROmpt.DTO.GradeDTO;
import com.example.PROmpt.DTO.ResponseDTO;
import com.example.PROmpt.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResponseController extends AplicationController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        return new ResponseEntity<>(responseService.test(), HttpStatus.OK);
    }

    @GetMapping("/responseByLLMId")
    public ResponseEntity<Object> getAllByLLMId(@RequestParam Long llmid){
        return new ResponseEntity<>(responseService.responsesByLLM(llmid),HttpStatus.OK);
    }

    @GetMapping("/responses")
    public ResponseEntity<Object> userPrompts(@RequestParam(required = false) Long userId){
        return new ResponseEntity<>(responseService.getPrompts(userId),HttpStatus.OK);
    }

    @PatchMapping("/gradeResponse")
    public ResponseEntity<Object> gradeResponse(@RequestBody GradeDTO gradeDTO){
        System.out.println(gradeDTO.getResponseReview());
        System.out.println(gradeDTO.getReview());
        return new ResponseEntity<>(responseService.gradeResponse(gradeDTO),HttpStatus.OK);
    }


    @GetMapping("/myResponses")
    public ResponseEntity<Object> myPrompts(){
        return new ResponseEntity<>(responseService.getPrompts(currentUser.getId()),HttpStatus.OK);
    }


    @PostMapping("/response")
    public ResponseEntity<Object> print(@RequestBody ResponseDTO responseDTO){
        System.out.println("u controleru sam");
        return new ResponseEntity<>(responseService.save(responseDTO),HttpStatus.OK);
    }
    //treba napraviti konstruktor u userPromptu koji odgovara podatkcima koji se salju kako bi se odmah to pretvorilo u UserPrompt
}
