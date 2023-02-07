package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.QuestionDto;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/questions")
@RestController
public class QuestionsController {
    private final QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/all")
    public QuestionDto testsQuestionnaire(){
        return questionService.getQuestionnaire();
    }
}
