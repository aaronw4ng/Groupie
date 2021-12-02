Feature: Set yourself unavailable to others with date option
Scenario: Set yourself unavailable to others indifinetly
    Given user is on the User Privacy page
    When user sets himself unavailable to others indefinitely
    Then user should see that he is unavailable to others indefinitely
    