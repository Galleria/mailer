*** Settings ***
Resource          resource.robot

*** Test Cases ***
Send button should be disabled when open application
    Open Browser To Main Page
    Element Should Be Disabled    send
    [Teardown]    Close Browser

Send button should be enabled when enter all required fields
    Open Browser To Main Page
    Input Text  to   penny@hotmail.com
    Input Text  topic   Test Topic
    Select Frame    xpath=//iframe
    Input Text  xpath=//body    TEST BODY
    Press Key   xpath=//body    \\09
    Unselect Frame
    Sleep  1 seconds
    Element Should Be Enabled   send
    [Teardown]    Close Browser











