*** Settings ***
RESOURCE            resource_add_contact.robot
SuiteTeardown       Close Browser

*** Test Cases ***
Open Mailer Page
    Open Browser  ${url}
    Sleep  ${delay} seconds

Go To Contract Page
    Click Contract Button
    Sleep  ${delay} seconds

Invalid Email Format
    Sleep  ${delay} seconds
    Set Name Field  ${EMPTY}
    Set Last Name Field  ${EMPTY}
    Set Email Field  ${EMPTY}
    Set Name Field  Jack
    Set Last Name Field  Dawson
    Set Email Field  abc
    Sleep  ${delay} seconds
    Click Add Button
    ${popup_msg}  Get Popup Message
    Should Be Equal As Strings  ${popup_msg}  Email format is invalid
