package com.ecoassitant.back.steps;

import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.entity.ConstanteEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@SpringBootTest
public class ConstanteStepdefs {
    private String baseUrl = "http://localhost:8001";
    private RestTemplate restTemplate;
    private ResponseEntity<ConstanteEntity> registerToken;
    @When("the user calls {string} with the id {int}")
    public void theUserCallsApiConstanteWithTheId(String path,int arg0) {
        restTemplate= new RestTemplate();
        registerToken = restTemplate.getForEntity(baseUrl+path+"?id= "+arg0, ConstanteEntity.class);
    }


    @And("the user receives from the server the constante id {int}")
    public void theUserReceivesFromTheServerTheConstanteId(int arg0) {
    }

    @Then("the user receives the status code {int} from {string}\"")
    public void theUserReceivesTheStatusCodeFromApiConstante(int arg0) throws Throwable {    // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();}
    }
}