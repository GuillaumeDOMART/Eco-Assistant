package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller with api linked to Questions
 */
@RequestMapping("api")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    /**
     * Constructor for QuestionController
     * @param questionService QuestionService
     */
    @Autowired
    public QuestionController(QuestionService questionService) {
        Objects.requireNonNull(questionService);
        this.questionService = questionService;
    }

    /**
     * Api for get quiz
     *
     * @return quiz type Map
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<Phase, List<QuestionUniqueDto>>> testsQuestionnaire() {
        var quiz = questionService.getQuestionnaire();
        return quiz != null ? new ResponseEntity<>(quiz, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Api for get quiz with the response of the project complete previously
     * @param id id of the project
     * @return quiz type Map
     */
    @PostMapping("/questions")
    public ResponseEntity <Map<Phase, List<QuestionUniqueDto>>> modifierQuestionnaire(@RequestBody  IdDto id){
        var quiz = questionService.completQuiz(id.getId());
        return quiz != null ? new ResponseEntity<>(quiz, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
