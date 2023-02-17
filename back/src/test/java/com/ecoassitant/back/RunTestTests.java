package com.ecoassitant.back;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty", features = "src/test/resources/features", glue = "com.ecoassitant.back.cucumber.steps"
)
public class RunTestTests {
}
