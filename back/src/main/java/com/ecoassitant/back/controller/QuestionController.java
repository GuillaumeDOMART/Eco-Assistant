package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.QuestionDto;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller with api linked to Questions
 */
@RequestMapping("api")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/questions")
    public ResponseEntity<QuestionDto> testsQuestionnaire(){
        QuestionDto question =  questionService.getQuestionnaire();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(question == null){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(question,headers,HttpStatus.OK);
        }
    }
    //TODO add question in the tree
//    @PostMapping("/question")
//    public ResponseEntity<QuestionDto> addQuestion(){
//
//    }

}
