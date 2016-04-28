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