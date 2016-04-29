*** Settings ***
RESOURCE            resource.robot
Test Teardown       Close All Browsers
*** Test Cases ***
My Test
   Open Browser To Main Page
   Input Email Form    penny@hotmail.com   Test Topic  TESTclickbutton

   Select Frame    xpath=//iframe
   Double Click Element  xpath=//body
   Unselect Frame

   Click Link  jquery=a.cke_button__bold
   Click Link  jquery=a.cke_button__italic
   Click Link  jquery=a.cke_button__underline
   Click Link  jquery=a.cke_button__strike
   Click Link  jquery=a.cke_button__numberedlist

#   Click Link  jquery=a.cke_button__image
#   Input Text Into Prompt  http://www.agilenutshell.com/assets/methods/scrum/scrum-overview.png