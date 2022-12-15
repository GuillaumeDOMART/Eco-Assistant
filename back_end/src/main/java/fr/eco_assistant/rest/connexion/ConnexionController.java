package fr.eco_assistant.rest.connexion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("connexion")
public class ConnexionController {

    @PostMapping("/")
    public ResponseEntity<String> connexion(){
        //TODO
        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }
}
