package fr.eco_assistant.rest.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question")
public class QuestionController {

    @PostMapping("/")
    public ResponseEntity<String> compute(){
        //TODO
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
