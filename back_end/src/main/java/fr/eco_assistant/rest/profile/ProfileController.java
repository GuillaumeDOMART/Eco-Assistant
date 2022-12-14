package fr.eco_assistant.rest.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Profile profile){
        return new ResponseEntity<>("yo yo yo", HttpStatus.NOT_FOUND);
    }
}
