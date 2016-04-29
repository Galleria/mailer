*** Settings ***
Library           Selenium2Library
Library           String

*** Variables ***
${SERVER}         dev.geekybase.local:8000
${BROWSER}        Google Chrome
${DELAY}          0
${MAIN URL}       http://${SERVER}/
${REMOTE_URL}     http://192.168.88.10:4444/wd/hub

*** Keywords ***
Open Browser To Main Page
    Open Browser    ${MAIN URL}    ${BROWSER}  None  ${REMOTE_URL}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Main Page Should Be Open
    Sleep   ${DELAY}

Main Page Should Be Open
    Title Should Be    Title

Input Email Form
    [Arguments]     ${to}   ${topic}    ${bodytext}
    Input Text  to   ${to}
    Sleep   ${DELAY}
    Input Text  topic   ${topic}
    Sleep   ${DELAY}
    Select Frame    xpath=//iframe
    Input Text  xpath=//body    ${bodytext}
    Sleep   ${DELAY}
    Unselect Frame
    Sleep  1 seconds