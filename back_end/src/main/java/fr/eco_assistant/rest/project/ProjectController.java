package fr.eco_assistant.rest.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @PostMapping("/add")
    public ResponseEntity<String> add(){
        //TODO
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
