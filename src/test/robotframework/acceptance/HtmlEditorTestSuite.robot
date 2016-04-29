*** Settings ***
RESOURCE            resource.robot
Test Teardown       Close All Browsers

*** Variable ***
${delay}  3

*** Test Cases ***
My Test
   Open Browser To Main Page
   Input Email Form    penny@hotmail.com   Test Topic  TESTclickbutton

   Select Frame    xpath=//iframe
   Double Click Element  xpath=//body
   Unselect Frame

   Sleep  ${delay} seconds

   Click Link  jquery=a.cke_button__bold
   Sleep  ${delay} seconds

   Click Link  jquery=a.cke_button__italic
   Sleep  ${delay} seconds

   Click Link  jquery=a.cke_button__underline
   Sleep  ${delay} seconds

   Click Link  jquery=a.cke_button__strike
   Sleep  ${delay} seconds

   Click Link  jquery=a.cke_button__numberedlist
   Sleep  ${delay} seconds

#   Click Link  jquery=a.cke_button__image
#   Input Text Into Prompt  http://www.agilenutshell.com/assets/methods/scrum/scrum-overview.png