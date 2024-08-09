Feature: Leave Application validations

  Background:
    Given user launches OrangeHRM
    And user enters "Admin" and "admin123"
    When user presses enter key on keyboard
    Then user logs in successfully and lands on "dashboard" url

  Scenario: verify apply leave screen is available
    Given user navigates to Leave menu
    When user clicks apply leave button
    Then "applyLeave" screen can be viewed
#
#  Scenario: personal leave option available
#      Given admin is logged in and on apply leave page
#      When user selects leave type from the leave screen
#      Then leave balance can be viewed
#
#  Scenario: date can be selected from date picker
#    Given admin is logged in and on apply leave page
#    When user selects "2029-08-14" as from date on the leave screen
#    And user selects "2030-09-14" as to date on the leave screen
#    And user enters as "Sick leave" in comments section
#    And user clicks Apply button
#    Then Success message can be viewed by the user
