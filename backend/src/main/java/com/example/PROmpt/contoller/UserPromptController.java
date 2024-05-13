package com.example.PROmpt.contoller;


import com.example.PROmpt.DTO.UserPromptDTO;
import com.example.PROmpt.models.UserPrompt;
import com.example.PROmpt.service.UserPromptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserPromptController extends AplicationController {

    private final UserPromptService userPromptService;

    public UserPromptController(UserPromptService userPromptService) {
        this.userPromptService = userPromptService;
    }

    @GetMapping("/userPrompts")
    public ResponseEntity<Object> userPrompts(@RequestParam(required = false) Long userId){
        return new ResponseEntity<>(userPromptService.getPrompts(userId),HttpStatus.OK);
    }


    @GetMapping("/myUserPrompts")
    public ResponseEntity<Object> myPrompts(){
        return new ResponseEntity<>(userPromptService.getPrompts(currentUser.getId()),HttpStatus.OK);
    }



    @PostMapping("/enhanceUserPrompt")
    public ResponseEntity<Object> storeUserPrompt(@RequestBody UserPromptDTO userPromptDTO, @RequestParam String llm){
        System.out.println("enhance kontoler");
        System.out.println(userPromptDTO.getTemperature());
        return new ResponseEntity<>(userPromptService.enhance(userPromptDTO,llm,currentUser), HttpStatus.OK);
    }
}
