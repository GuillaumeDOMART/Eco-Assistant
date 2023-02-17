Feature: Constante
  Scenario: User wants to get the constante associated to an id
    When the user calls '/api/constante' with the id 1
    Then the user receives the status code 200 from '/api/constante'"
    And the user receives from the server the constante id 1
