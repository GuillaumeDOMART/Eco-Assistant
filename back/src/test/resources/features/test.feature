Feature: Test
  Scenario: User wants to test the response of API with a GET
    When the user calls '/api/test'
    And the user receives from the server the boolean true