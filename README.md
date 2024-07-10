# MessageAPIAutomation
Automation script to test Message API of Perry's Summer Camp.

Preconditions to execute the test script:
-----------------------------------------
The Node.js application should be up and running in the local machine.
Java, Maven to be installed and configured in the local machine.


Procedure to run the test script:
---------------------------------
A. Running from the Terminal or Command Prompt:
Launch the Terminal or Command Prompt from the project directory
Enter the command "mvn clean install test"

B. Running from an IDE:
Import the test suite as a Maven Project
Under src/test/java, open the com.perry.test package
Select the "ListMessages.java" class and select Run As TestNG Test


Test Scenarios:
---------------
1. validate if message can be sent from user with fromId to user with toId in Create Message API
2. validate if a reply can be sent back by user with toId to user with fromId in Create Message API
3. validate if consecutive messages can be sent by the same user to the same receiver in Create Message API
4. validate error response when fromId is null in Create Message API
5. validate error response when toId is null in Create Message API
6. validate error response when message is null in Create Message API
7. validate if a message can be deleted using messageId in Delete Message API
8. validate if a message can be retrieved using messageId in Get Message API
9. validate error response when a deleted message is retrieved using messageId in Get Message API
10. validate error response when a messageId used for retrieval is invalid/non existent in Get Message API
11. validate if list of messages sent by user with toId to user with fromId can be retrieved in List Message API
12. validate if list of messages sent by user with fromId to user with toId can be retrieved in List Message API
13. validate error response when no message are sent by user with fromId to user with toId in List Message API
14. validate error response if either of fromId or toId is empty in List Message API


Bugs:
-----
1. In List Message API, the response shows all the messages sent by the user with 'fromId' and all the messages received by the user with 'toId'. The messages sent by the user with 'fromId' to the user with 'toId' are not retrieved. (Test14 in ListMessage.java)

2. In List Message API, no error is given when either 'fromId' or 'toId' is not given (Test15 in ListMessage.java)

3. In Create Message API, no error is given when message is blank ("message": "")

4. Getting below error from the application when a deleted messageId is again deleted, but the response is appearing as expected (Test8 in DeleteMessage.java).
Error [ERR_HTTP_HEADERS_SENT]: Cannot remove headers after they are sent to the client
    at ServerResponse.removeHeader (node:_http_outgoing:808:11)
    at ServerResponse.send (D:\Github\perrys-summer-camp\node_modules\express\lib\response.js:215:10)
    at ServerResponse.json (D:\Github\perrys-summer-camp\node_modules\express\lib\response.js:279:15)
    at D:\Github\perrys-summer-camp\dist\index.js:55:28
    at Generator.next (<anonymous>)
    at fulfilled (D:\Github\perrys-summer-camp\dist\index.js:5:58)
    at process.processTicksAndRejections (node:internal/process/task_queues:95:5) {
  code: 'ERR_HTTP_HEADERS_SENT'


Findings:
---------
1. No error message/warning when from.id and to.id is same in Create message API.
2. No error message/warning when from.id or to.id is used for the first time and not created using User API.
