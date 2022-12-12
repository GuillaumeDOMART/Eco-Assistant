package fr.eco_assistant.frontend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    List<String> test(){
        return List.of(new String[]{"toto", "titi"});
    }

}
