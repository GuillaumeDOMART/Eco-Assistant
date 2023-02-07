package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.QuestionDto;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller with api linked to Questions
 */
@RequestMapping("api/questions")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/all")
    public QuestionDto testsQuestionnaire(){
        return questionService.getQuestionnaire();
    }
}
