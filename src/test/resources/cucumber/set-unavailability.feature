Feature: Set yourself unavailable to others with date option
# Scenario: Set yourself unavailable to others indefinitely, you should see yourself unavailable in status
#     Given a new user is set up
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user types in "0" in unavailable time box
#     And user clicks Set Unavailable button
#     Then user should see themselve unavailable until indefinitely

# Scenario: Set yourself unavailable to others for 1 hour, you should see yourself unavailable for 1 hour in status
#     Given a new user is set up
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user types in "1" in unavailable time box
#     And user clicks Set Unavailable button
#     Then user should see themselve unavailable until some time in the future

# Scenario: Set yourself available to others when unavailable, you should see yourself available in status
#     Given a new user is set up
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user types in "1" in unavailable time box
#     And user clicks Set Unavailable button
#     And user logs out
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user clicks Set Available button
#     Then user should see themselve available

# Scenario: Set yourself unavailable to others for 10 seconds, you should see yourself unavailable for some time. after 15 seconds, you should see yourself available 
#     Given a new user is set up
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user types in "0.003" in unavailable time box
#     And user clicks Set Unavailable button
#     And user logs out
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     And user should see themselve unavailable until some time in the future
#     And user logs out
#     And wait for 10 seconds
#     And new user one logs in
#     And user navigates to Privacy Settings page
#     Then user should see themselve available

Scenario: Set yourself unavailable to others indefinitely, other user should see you unavailable
    Given two new users are set up
    And new user one logs in
    And user navigates to Privacy Settings page
    And user types in "0" in unavailable time box
    And user clicks Set Unavailable button
    And user logs out
    And new user two logs in
    And user manually types in the create proposal page
    And user clicks add users button
    And user types in newUser1 in the invitee search box
    Then user should see newUser1 unavailable not clickable