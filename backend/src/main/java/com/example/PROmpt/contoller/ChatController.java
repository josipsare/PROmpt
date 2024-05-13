package com.example.PROmpt.contoller;

import com.example.PROmpt.DTO.ChatRequest;
import com.example.PROmpt.DTO.ChatResponse;
import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.LLM;
import com.example.PROmpt.models.Response;
import com.example.PROmpt.repository.LLMRepo;
import com.example.PROmpt.repository.ResponseRepo;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ChatController extends AplicationController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate openAiRestTemplate;

    @Qualifier("llamaaiRestTemplate")
    @Autowired
    private RestTemplate llamaAiRestTemplate;

    @Autowired
    private ResponseRepo responseRepo;

    @Autowired
    private LLMRepo llmRepo;

    private String model;

    private String apiUrl;

    @GetMapping("/chat")
    public Object chat(FinishedPrompt finishedPrompt,Long llmID,Double temperature) {
        String prompt= finishedPrompt.getText();

        System.out.println(prompt);
        // create a request

        if(llmID==1){
            model="gpt-3.5-turbo";
            apiUrl="https://api.openai.com/v1/chat/completions";
        }else{
            model="llama-13b-chat";
            apiUrl="https://api.llama-api.com/chat/completions";
        }

        ChatRequest request = new ChatRequest(model, prompt);
        request.setTemperature(temperature);
        System.out.println(request);
        ChatResponse response;

        if(llmID==1){
            response = openAiRestTemplate.postForObject(apiUrl, request, ChatResponse.class);
        }else{
            response = llamaAiRestTemplate.postForObject(apiUrl, request, ChatResponse.class);
        }

        Response responseToReturn;

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }else{
            System.out.println("RESPONSE");
            System.out.println(response);
            String text = response.getChoices().get(0).getMessage().getContent();
            int promptTokens = response.getUsage().getPrompt_tokens();
            int responseTokens = response.getUsage().getCompletion_tokens();
            LLM modelUsed = llmRepo.getReferenceById(llmID);
            System.out.println(modelUsed);
            System.out.println("FinishedPrompt u chat metodi");
            System.out.println(finishedPrompt.getUserPrompt());
            Response responseToBeSaved = new Response(text,text.length(),modelUsed,finishedPrompt.getUserPrompt(),finishedPrompt,promptTokens,responseTokens);
            responseToReturn= responseRepo.save(responseToBeSaved);
            List<Response> listaResponsea = modelUsed.getResponses();

            //todo provjeriti jeli ovaj dio potreban
            listaResponsea.add(responseToReturn);
            modelUsed.setResponses(listaResponsea);
            llmRepo.save(modelUsed);


            System.out.println("responseToReturn");
            System.out.println(responseToReturn.getText());

        }

        // return the first response
        return responseToReturn;
    }
}
