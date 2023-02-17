Feature: Authentication
  Scenario: User wants to register himself
    When the user calls '/api/auth/register' with 'test@test.fr'
    Then the user receives the status code 200
    And the user receives from the server the mail 'test@test.fr'


  Scenario: User wants to login
    When the user calls '/api/auth/authentication' with 'test@test.fr'
    Then the user receives the status code 200
    And the user receives from the server the mail 'test@test.fr'
