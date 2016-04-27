*** Settings ***
Documentation     A test suite with a single test for sender page.
...
...               This test has a workflow that is created using keywords in
...               the imported resource file.
Resource          resource.robot

*** Test Cases ***
Enable/Disable Send Button
    Open Browser To Main Page
    Element Should Be Disabled    send
    [Teardown]    Close Browser
