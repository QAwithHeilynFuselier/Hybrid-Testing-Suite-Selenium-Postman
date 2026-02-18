Feature: Login feature
  Scenario: Positive Login Test
    Given  I open Koel Login Page
    When I enter email "heilyn.fuselier@testpro.io"
    And I enter password "NewYear@2026"
    And I submit
    Then I am Logged in