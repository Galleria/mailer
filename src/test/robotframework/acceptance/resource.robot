*** Settings ***

Library           Selenium2Library

*** Variables ***
${SERVER}         dev.geekybase.local:8000
${BROWSER}        Google Chrome
${DELAY}          0
${MAIN URL}       http://${SERVER}/
${REMOTE_URL}     http://192.168.88.10:4444/wd/hub

*** Keywords ***
Open Browser To Main Page
    Open Browser    ${MAIN URL}    ${BROWSER}  None  http://192.168.88.10:4444/wd/hub
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Main Page Should Be Open

Main Page Should Be Open
    Title Should Be    Title
    Close All Browsers