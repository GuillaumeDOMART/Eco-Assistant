package com.ecoassitant.back.steps;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest
public class TestSteps {
    private String response;
    private RestTemplate restTemplate;

    /**
     * Scenario: User wants to test the response of API with a GET
     */

    @When("the user calls {string}")
    public void theUserCallsApiTest() {
        restTemplate = new RestTemplate();
        String url = "http://localhost:8001/api/test";
        this.response = restTemplate.getForObject(url,String.class);
    }


    @And("the user receives from the server the boolean true")
    public void theUserReceivesFromTheServerTheBooleanTrue() {
        assertEquals("True",response);
    }
}
