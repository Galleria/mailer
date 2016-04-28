*** Settings ***

Library           Selenium2Library

*** Variables ***
${SERVER}         localhost:8080
${BROWSER}        Firefox
${DELAY}          0
${MAIN URL}      http://${SERVER}/

*** Keywords ***
Open Browser To Main Page
    Open Browser    ${MAIN URL}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Main Page Should Be Open

Main Page Should Be Open
    Title Should Be    Title

