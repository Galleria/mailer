*** Settings ***
RESOURCE            resource.robot
Test Teardown       Close All Browsers
*** Test Cases ***
My Test
   Open Browser To Main Page