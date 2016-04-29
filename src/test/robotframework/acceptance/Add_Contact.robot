*** Settings ***
RESOURCE            resource_add_contact.robot
SuiteTeardown       Close Browser

*** Test Cases ***
Open Mailer Page
    Open Browser  ${clearContact}
    Close Browser
    Open Browser  ${url}
    Sleep  ${delay} seconds

Go To Contract Page
    Click Contract Button
    Sleep  ${delay} seconds

Add First Contract
    Modify Contract  Jack  Dawson  JDawson@titanic.com  1

Add Second Contract
    Modify Contract  Rose  Flower  RFlower@titanic.com  2

Add Contract Duplicate Email
    Modify Contract  Jack  Kitty  JDawson@titanic.com  2
    Close Browser
    Open Browser  ${restoreContact}

*** Keywords ***
Modify Contract
    [Arguments]  ${firstName}  ${lastName}  ${email}  ${expected_row}
    Validate Input Fields
    Set Name Field  ${firstName}
    Set Last Name Field  ${lastName}
    Set Email Field  ${email}
    Sleep  ${delay} seconds
    Click Add Button
    Sleep  ${delay} seconds
    ${rowCnt}=  Count All Contacts
    Should Be Equal As Integers  ${expected_row}  ${rowCnt}
    Validate Input Fields
    Check Contract Exist  ${firstName}  ${lastName}  ${email}

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
