package com.ecoassitant.back.steps;

import com.ecoassitant.back.dto.RegisterInputDto;
import com.ecoassitant.back.dto.TokenDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest
public class AuthenticationSteps {
    private String baseUrl = "http://localhost:8001";
    private String mail;
    private RestTemplate restTemplate;
    private ResponseEntity<TokenDto> registerToken;

    @When("the user calls {string} with {string}")
    public void theUserCallsApiAuthRegisterWithTestTestFr(String api, String mail) {
        restTemplate= new RestTemplate();
        var dto = new RegisterInputDto();
        this.mail=mail;
        //this.registerToken = restTemplate.postForEntity(baseUrl+api,,TokenDto.class);

    }

    @Then("the user receives the status code {int}")
    public void theUserReceivesTheStatusCode(int arg0) {
        HttpStatus currentStatusCode = registerToken.getStatusCode();
        assertEquals(arg0,currentStatusCode.value());
    }

    @And("the user receives from the server the mail {string}")
    public void theUserReceivesFromTheServerTheTestTestFr(String mail) {

    }
}
