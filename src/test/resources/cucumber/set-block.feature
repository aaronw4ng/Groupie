Feature: Block other users on Privacy Page
Scenario: If user 1 blocks user 2, user 1 should see user 2 on the block list
    Given two new users are set up
    And new user one logs in
    And user navigates to Privacy Settings page
    And user types in newUser2 in the block search box
    And user clicks block button
    Then user should see newUser2 on the block list

Scenario: If user 1 blocks user 2, user 2 should not be able to invite user 1 to a group proposal
    Given two new users are set up
    And new user one logs in
    And user navigates to Privacy Settings page
    And user types in newUser2 in the block search box
    And user clicks block button
    And user logs out
    And new user two logs in
    And user manually types in the create proposal page
    And user clicks add users button
    And user types in newUser1 in the invitee search box
    Then user should see newUser1 unavailable not clickable

Scenario: if user 1 blocks user 2 and later unblocks user2, user 2 should be able to invite user 1 to a group proposal
    Given two new users are set up
    And new user one logs in
    And user navigates to Privacy Settings page
    And user types in newUser2 in the block search box
    And user clicks block button
    And user clicks newUser1 in the block list
    And user clicks unblock button
    And user logs out
    And new user two logs in
    And user manually types in the create proposal page
    And user clicks add users button
    And user types in newUser1 in the invitee search box
    Then user should see newUser1 available and clickable