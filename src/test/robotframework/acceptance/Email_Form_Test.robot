*** Settings ***
Resource          resource.robot
Test Teardown     Close All Browsers

*** Test Cases ***
Send button should be disabled when open application
    Open Browser To Main Page
    Element Should Be Disabled    send

Send button should be enabled when enter all required fields
    Open Browser To Main Page
    Input Email Form    penny@hotmail.com   Test Topic  TEST BODY
    Element Should Be Enabled   send

Send button should be disabled when recipients is empty
    Open Browser To Main Page
    Input Email Form    ${EMPTY}   Test Topic  TEST BODY
    Element Should Be Disabled   send

Send button should be disabled when topic is empty
    Open Browser To Main Page
    Input Email Form    penny@hotmail.com   ${EMPTY}    TEST BODY
    Element Should Be Disabled   send

Send button should be disabled when body is empty
    Open Browser To Main Page
    Input Email Form    penny@hotmail.com   Test Topic  ${EMPTY}
    Element Should Be Disabled   send

Send button should send an email
    Open Browser To Main Page
    Input Email Form    penny.inspector.gadget1@gmail.com   Test Topic  TEST BODY
    Click Button  send
    Wait Until Page Contains Element  next
    Element Text Should Be  xpath=//li[1]/span  penny.inspector.gadget1@gmail.com
    Click Button  next
    Wait Until Page Contains Element  send
    Element Should Be Visible  send

Send button should send an email if there is a duplicated
    Open Browser To Main Page
    Input Email Form    penny.inspector.gadget1@gmail.com,penny.inspector.gadget1@gmail.com   Test Topic  TEST BODY
    Click Button  send
    Wait Until Page Contains Element  next
    Xpath Should Match X Times  //li/span  1
    Click Button  next
    Wait Until Page Contains Element  send
    Element Should Be Visible  send

Send button should send multiple emails
    Open Browser To Main Page
    Input Email Form    penny.inspector.gadget1@gmail.com, penny.inspector.gadget2@gmail.com    Test Topic  TEST BODY
    Click Button  send
    Wait Until Page Contains Element  next
    Element Text Should Be  xpath=//li[1]/span  penny.inspector.gadget1@gmail.com
    Element Text Should Be  xpath=//li[2]/span  penny.inspector.gadget2@gmail.com
    Click Button  next
    Wait Until Page Contains Element  send
    Element Should Be Visible  send

Topic cannot input more than 500 characters
    Open Browser To Main Page
    ${inputTopic}=     Generate Random String  550
    Input Text  topic   ${inputTopic}
    ${actualTopic}=    Get Value    topic
    ${length}=      Get Length  ${actualTopic}
    Should Be Equal As Integers     ${length}   500

Body cannot input more than 2000 characters
    Open Browser To Main Page
    ${inputBody}=     Generate Random String  2500
    Select Frame    xpath=//iframe
    Input Text  xpath=//body    ${inputBody}
    ${actualBody}=    Get Text    xpath=//body
    ${length}=      Get Length  ${actualBody}
    Should Be Equal As Integers     ${length}   2000
