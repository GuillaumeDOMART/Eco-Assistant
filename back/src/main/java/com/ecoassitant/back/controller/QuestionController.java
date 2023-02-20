package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    /**
     * Api for get quiz
     * @return quiz type Map
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<Phase, List<QuestionUniqueDto>>> testsQuestionnaire(){
        var quiz = questionService.getQuestionnaire();
        return quiz != null? new ResponseEntity<>(quiz, HttpStatus.OK): new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
