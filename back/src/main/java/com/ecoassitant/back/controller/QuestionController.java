package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.dto.PhaseDto;
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
     * Api for get a Quiz by phase
     * @param phaseDto phase of the project
     * @return list of question of the phase
     */
    @PostMapping("/questions/phase")
    public ResponseEntity<List<QuestionUniqueDto>> getPhaseProjet(@RequestBody PhaseDto phaseDto) {
        var quiz = questionService.completPhase(phaseDto);
        return quiz.isEmpty() ? new ResponseEntity<>(quiz, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
