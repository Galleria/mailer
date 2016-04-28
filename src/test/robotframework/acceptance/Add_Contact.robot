*** Settings ***
Library		com.mailer.robot.MailerKeyword
SuiteTeardown  Close Browser

*** Variables ***
${url}  http://dev.geekybase.local:8000
${delay}  0

*** Test Cases ***
Open Mailer Page
    Open Browser  ${url}
    Sleep  ${delay} seconds

Go To Contract Page
    [Tags]  work_in_progress
    Click Contract Button
    Sleep  ${delay} seconds

Add First Contract
    [Tags]  work_in_progress
    Set Name Field  Jack
    Set Last Name Field  Dawson
    Set Email Field  JDawson@titanic.com
    Click Add Button
    ${rowCnt}=  Count All Contacts
    Sleep  ${delay} seconds
#    Should Be Equal As Integers  2  ${rowCnt}
#    Validate Input Fields
    Check Contract Exist  Jack  Dawson  JDawson@titanic.com


Add Second Contract
    [Tags]  work_in_progress
    Set Name Field  Rose
    Set Last Name Field  Flower
    Set Email Field  RFlower@titanic.com
    Click Add Button
    ${rowCnt}=  Count All Contacts
    Sleep  ${delay} seconds
    Should Be Equal As Integers  2  ${rowCnt}
    Validate Input Fields

Add Contract Duplicate Email
    [Tags]  work_in_progress
    Set Name Field  Jack
    Set Last Name Field  Kitty
    Set Email Field  JDawson@titanic.com
    Click Add Button
    ${rowCnt}=  Count All Contacts
    Sleep  ${delay} seconds
    Should Be Equal As Integers  2  ${rowCnt}
    Validate Input Fields

*** Keywords ***
Validate Input Fields
    ${name}=  Get Name Field
    ${lastName}=  Get Last Name Field
    ${email}=  Get Email Field
    Should Be Equal As Strings  ${name}  ${EMPTY}
    Should Be Equal As Strings  ${lastName}  ${EMPTY}
    Should Be Equal As Strings  ${email}  ${EMPTY}
    ${buttonState}=  Get Button State
    Should Be Equal  ${buttonState}  Disabled

Check Contract Exist
    [Arguments]  ${name}  ${lastName}  ${email}
    ${isExisted}=  Is Contract Exist In Table  ${name}  ${lastName}  ${email}
    Should Be true  ${isExisted}

