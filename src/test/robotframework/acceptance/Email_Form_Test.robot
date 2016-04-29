*** Settings ***
Resource          resource.robot
Test Setup        Set Selenium Timeout	10 seconds
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
    ${TO_EMAILS}=  Generate To Email Address  2  ,
    ${TO_EMAILS}=  Replace String  ${TO_EMAILS}  penny.inspector.gadget2@gmail.com  penny.inspector.gadget1@gmail.com
    Open Browser To Main Page
    Input Email Form    ${TO_EMAILS}   Test Topic  TEST BODY
    Click Button  send
    Wait Until Page Contains Element  next
    Xpath Should Match X Times  //li/span  1
    Click Button  next
    Wait Until Page Contains Element  send
    Element Should Be Visible  send

Send button should send an email if there is a duplicated even it is exceed the maximum limit
    ${TO_EMAILS}=  Generate To Email Address  22  ,
    ${TO_EMAILS}=  Replace String Using Regexp  ${TO_EMAILS}  \\d+  1
    Open Browser To Main Page
    Input Email Form    ${TO_EMAILS}   Test Topic  TEST BODY
    Click Button  send
    Wait Until Page Contains Element  next
    Xpath Should Match X Times  //li/span  1
    Click Button  next
    Wait Until Page Contains Element  send
    Element Should Be Visible  send

Send button should send multiple emails
    ${TO_EMAILS}=  Generate To Email Address  2  , \
    Open Browser To Main Page
    Input Email Form    ${TO_EMAILS}    Test Topic  TEST BODY
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
    Sleep  1 seconds
    Input Text  topic   ${inputTopic}
    Sleep   ${DELAY}
    ${actualTopic}=    Get Value    topic
    ${length}=      Get Length  ${actualTopic}
    Should Be Equal As Integers     ${length}   500

Body cannot input more than 2000 characters
    Open Browser To Main Page
    ${inputBody}=     Generate Random String  2500
    Sleep  1 seconds
    Select Frame    xpath=//iframe
    Input Text  xpath=//body    ${inputBody}
    Sleep   ${DELAY}
    ${actualBody}=    Get Text    xpath=//body
    ${length}=      Get Length  ${actualBody}
    Should Be Equal As Integers     ${length}   2000

Send button should show error if maximum to emails are exceed
    ${TO_EMAILS}=  Generate To Email Address  21  ,
    Open Browser To Main Page
    Input Email Form    ${TO_EMAILS}    Test Topic  TEST BODY
    Click Button  send
    Sleep  2 seconds
    Element Should Be Visible  css=.message
    Element Text Should Be  css=.message  Maximum email is 20.

Send button should show error if email format is no host
    Open Browser To Main Page
    Input Email Form    penny    Test Topic  TEST BODY
    Click Button  send
    Sleep  2 seconds
    Element Should Be Visible  css=.message
    Element Text Should Be  css=.message  Invalid email format.

Send button should show error if email format is no suffix
    Open Browser To Main Page
    Input Email Form    penny@gmail    Test Topic  TEST BODY
    Click Button  send
    Sleep  2 seconds
    Element Should Be Visible  css=.message
    Element Text Should Be  css=.message  Invalid email format.

*** Keywords ***
Generate To Email Address
    [Arguments]  ${number}  ${separator}
    ${TO_EMAILS}=  Set Variable  penny.inspector.gadget1@gmail.com
    :FOR  ${INDEX}  IN RANGE  2  ${number} + 1
    \   ${TO_EMAILS}=  Catenate  SEPARATOR=  ${TO_EMAILS}  ${separator}  penny.inspector.gadget  ${INDEX}  @gmail.com
    [return]  ${TO_EMAILS}
