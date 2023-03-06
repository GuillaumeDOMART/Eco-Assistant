package com.ecoassitant.back.controller;

import com.ecoassitant.back.dao.ProposedQuestionDao;
import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.dto.QuestionProposeDto;
import com.ecoassitant.back.service.ProposedQuestionService;
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
public class ProposedQuestionController {

    private final ProposedQuestionService proposedQuestionService;

    /**
     * Initialize the constantService
     * @param proposedQuestionService composite for using Service methods
     */
    @Autowired
    public ProposedQuestionController(ProposedQuestionService proposedQuestionService) {
        this.proposedQuestionService = proposedQuestionService;
    }


    /**
     *get the submitted question associated with the id
     * @param id represents id of the submitted question
     * @return submitted question associated with id
    */
    @GetMapping("/proposition/{id}")
    public ResponseEntity<QuestionProposeDto> getProposition(@PathVariable("id") int id){
        QuestionProposeDto qpdto = proposedQuestionService.getQuestionProposeByID(id);
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
        List<QuestionProposeDto> qps = proposedQuestionService.findAll();
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
    public ResponseEntity<IdDto> createProposition(@RequestBody ProposedQuestionDao qp){
        var canBeSaved = proposedQuestionService.saveQuestionPropose(qp);

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
