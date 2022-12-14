package fr.eco_assistant.rest.Computation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("computation")
public class ComputationController {

    @GetMapping("{id}")
    public ResponseEntity<String> compute(@PathVariable String id){
        //TODO
        return null;
    }
}
