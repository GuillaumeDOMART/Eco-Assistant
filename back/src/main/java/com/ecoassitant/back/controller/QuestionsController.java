package com.ecoassitant.back.controller;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/questions")
@RestController
public class QuestionsController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionsController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/all")
    public List<QuestionEntity> testsQuestionnaire(){
        return questionRepository.findAll();
    }
}
