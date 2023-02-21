package com.ecoassitant.back.controller;

import com.ecoassitant.back.dao.QuestionProposeDao;
import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.dto.QuestionProposeDto;
import com.ecoassitant.back.service.QuestionProposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for request about Constance
 */

@RequestMapping("api")
@RestController
public class QuestionProposeController {

    private final QuestionProposeService questionProposeService;

    /**
     * Initialize the constanteService
     * @param questionProposeService composite for using Service methods
     */
    @Autowired
    public QuestionProposeController(QuestionProposeService questionProposeService) {
        this.questionProposeService = questionProposeService;
    }


    /**
     *get the submitted question associated with the id
     * @param id represents id of the submitted question
     * @return submitted question associated with id
    */
    @GetMapping("/proposition/{id}")
    public ResponseEntity<QuestionProposeDto> getProposition(@PathVariable("id") int id){
        QuestionProposeDto qpdto = questionProposeService.getQuestionProposeByID(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(qpdto==null){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(qpdto,headers, HttpStatus.OK);
        }

    }

    /**
     * Get all submitted questions in the DB
     * @return all submitted questions
     */

    @GetMapping("/propositions")
    public ResponseEntity<List<QuestionProposeDto>> getAllPropositions(){
        List<QuestionProposeDto> qps = questionProposeService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(qps.isEmpty()){
            return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(qps,headers,HttpStatus.OK);
        }
    }

    /**
     * Create a proposition onto the database
     * */
    @PostMapping("/proposition")
    public ResponseEntity<IdDto> createProposition(@RequestBody QuestionProposeDao qp){
        var canBeSaved = questionProposeService.saveQuestionPropose(qp);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if(canBeSaved > 0){
            var iddto = new IdDto();
            iddto.setId(canBeSaved);
           return new ResponseEntity<>(iddto, headers, HttpStatus.OK);
       } else {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
