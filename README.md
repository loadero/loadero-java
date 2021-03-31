<h2>Java wrapper for Loadero API</h2>

<a href="https://code.tdlbox.com/mihhail.matisinets/loader-rest-api-wrapper/-/commits/master"><img alt="pipeline status" src="https://code.tdlbox.com/mihhail.matisinets/loader-rest-api-wrapper/badges/master/pipeline.svg" /></a>
<a href="https://code.tdlbox.com/mihhail.matisinets/loader-rest-api-wrapper/-/commits/master"><img alt="coverage report" src="https://code.tdlbox.com/mihhail.matisinets/loader-rest-api-wrapper/badges/master/coverage.svg" /></a>

<h3>Description</h3>
<hr>
<p>This is a small java library that can be used as a client to interact with Loadero API service.</p>
<p>
It allows you to create, delete, update, launch and retrieve information about tests from Loadero.
</p>
<h3>Documentation</h3>
<hr>
<h4>Classes</h4>
Classes that are mainly used to interact with Loadero API.
<table style="text-align: center; vertical-align: middle;">
<thead>
<tr>
<th>Name</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>

```java
class LoaderoClient(){}
```
</td>
<td>Main class to interact with Loadero API.</td>
</tr>
<tr>
<td>

```java
interface Model(){}
```
</td>
<td>Marker interface to group similar classes.</td>
</tr>
<tr>
<td>

```java
class Group(){}
```
</td>
<td>Class that is responsible to represent information about Loadero group.</td>
</tr>
<tr>
<td>

```java
class Test(){}
```
</td>
<td>
Class that represents information about test options that you can provide/retrieve
from Loadero API.
</td>
</tr>
<tr>
<td>

```java
class RunInfo(){}
```
</td>
<td>Class that is used to collect information about test runs.</td>
</tr>
<tr>
<td>

```java
class Participant(){}
```
</td>
<td>Class that represents information about Loadero participant.</td>
</tr>
<tr>
<td>

```java
class ScriptDetails(){}
```
</td>
<td>Class that is represent information about script file that is used
by Loadero to run tests against.
</td>
</tr>
<tr>
<td>

```java
class TestResults(){}
```
</td>
<td>Class that (will) represent information about Loadero test's results.</td>
</tr>
<tr>
<td>

```java
class TestRunResult(){}
```
</td>
<td>Class that is responsible for storing information about all test run results.</td>
</tr>
<tr>
<td>

```java
class TestRunParticipantResult(){}
```
</td>
<td>Class that is responsible to represent information about single participant test run result.</td>
</tr>

<tr>
<td>

```java
class BrowserType(){}
```
</td>
<td>Class that is used to represent different versions of browser.</td>
</tr>
<tr>
<td>

```java
class Asserts(){}
```
</td>
<td>Class that is used to create,update, retrieve and delete test's asserts.</td>
</tr>
</tbody>
</table>

<h4>Enums</h4>
Public Enums that can be used to set constant values for Loadero tests and participants.
<table style="text-align: left; vertical-align: middle; table-layout: fixed;wrap:break-word;">
<thead>
  <tr style="text-align: center; vertical-align: middle;">
    <th>Name</th>
    <th>Description</th>
  </tr>
</thead>
<tbody>
<tr>
<td>

```java
enum BrowserLatestType
```
</td>
<td>
Contains latest version of Chrome and Firefox browser that can be used for testing.
</td>
</tr>
<tr>
<td>

```java
enum ComputeUnitsType
```
</td>
<td>
Contains compute units options.
</td>
</tr>
<tr>
<td>

```java
enum IncrementStrategyType
```
</td>
<td>
Contains possible options for increment strategies.
</td>
</tr>
<tr>
<td>

```java
enum LocationType
```
</td>
<td>
Contains different geo locations that are available for testing.
</td>
</tr>
<tr>
<td>

```java
enum MediaType
```
</td>
<td>
Contains media options that can be used for testing.
</td>
</tr>
<tr>
<td>

```java
enum NetworkTypes
```
</td>
<td>
Contains different network conditions that can be set for testing.
</td>
</tr>
<tr>
<td>

```java
enum TestModeType
```
</td>
<td>
Contains options for setting testing mode.
</td>
</tr>
<tr>
<td>

```java
enum MachineAsserts
```
</td>
<td>
Contains values for all machine asserts paths.
</td>
</tr>
<tr>
<td>

```java
enum WebrtcAsserts
```
</td>
<td>
Contains values for all webrtc asserts paths.
</td>
</tr>
<tr>
<td>

```java
enum AssertOperator
```
</td>
<td>
Contains values for asserts operators.
</td>
</tr>
</tbody>
</table>


<h4>Methods</h4>
Public methods that is used to interact with Loadero API.
<table style="text-align: left; vertical-align: middle; table-layout: fixed;wrap:break-word;">
<thead>
  <tr style="text-align: center; vertical-align: middle;">
    <th>Name</th>
    <th>Parameters</th>
    <th>Description</th>
  </tr>
</thead>
<tbody>
<tr>
<td>

```java
Test getTestById(int testId)
```
</td>
<td>
<b>int testId</b> - ID of the test we would like to get.
</td>
<td>
Makes GET request to <b>/projects/{projectID}/tests/{testID}</b> endpoint and
retrieves information about existing test. Takes no arguments. Returns data as Test object.</td>
</tr>
<tr>
<td>

```java
Test updateTestById
        (int testId, Test newOptions)
```
</td>
<td>
<b>Test newOptions</b> - Required parameter that is used to set new test options
in Loadero via API call.

<b>int testId</b> - ID of the test we wish to update.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}</b> and updates existing test options 
in Loadero. 
Takes in Test object with desired params set through setter methods.
</td>
</tr>
<tr>
<td>

```java
Test createNewTest(Test newTest) 
```
</td>
<td>
<b>Test newTest</b> - Required parameter that is used to create new test in Loadero.
</td>
<td>
Makes POST request to <b>/projects/{projectID}/tests/</b> and creates new test.
</td>
</tr>
<tr>
<td>

```java
void deleteTestById(int id)
```
</td>
<td>
<b>int testIdt</b> - Required parameter that is used to delete specific test in Loadero.
</td>
<td>
Makes DELETE request to <b>/projects/{projectID}/tests/{testId}</b> and deletes test.
</td>
</tr>
<tr>
<td>

```java
ScriptDetails getTestScriptById(int fileId)
```
</td>
<td>
<b>int fileId</b> - ID that is pointing to the script file on Loadero.
</td>
<td>Makes GET request to <b>/projects/{projectID}/files/{fileID}/</b> and retrieves information
about the script content and where it's stored.
</td>
</tr>
<tr>
<td>

```java
Group getGroupById
        (int testId, int groupId)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int groupId</b> - ID of the group that is used to retrieve information about Loadero Group.
</td>
<td>Makes GET request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/</b> 
and retrieves information about group.
</td>
</tr>
<tr>
<td>

```java
Group updateGroupById
        (int testId, int groupId, Group newGroup)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int groupId</b> - ID of the group that is wish to be updated.<br>
<b>Group newGroup</b> - Group object with new params.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/</b> 
and updates information about group.
</td>
</tr>
<tr>
<td>

```java
Group createNewGroup
        (Group newGroup, int testId)
```
</td>
<td>
<b>int testId</b>  - ID of the test where group will be created.<br/>
<b>Group newGroup</b> - new Group object.
</td>
<td>Makes POST request to <b>/projects/{projectID}/tests/{testID}/groups/</b> 
and creates new group.
</td>
</tr>
<tr>
<td>

```java
void deleteGroupById
        (int testId, int groupId)
```
</td>
<td>
<b>int testId</b>  - ID of the test to be deleted.<br/>
</td>
<td>Makes DELETE request to <b>/projects/{projectID}/tests/{testID}/groups/{groupId}/</b> 
and deletes group.
</td>
</tr>
<tr>
<td>

```java
Participant getParticipantById
        (int testId, int groupId, int participnatId)
```
</td>
<td>
<b>int testId</b> - ID of the test containing participant.<br>
<b>int groupId</b> - ID of the group containing participant.<br>
<b>int participantId</b> - ID of the participant that is used to retrieve information.
</td>
<td>Makes GET request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/participants/{participantID}
</b> 
and retrieves information about participant.
</td>
</tr>
<tr>
<td>

```java
Participant updateTestParticipantById
        (int testId, int groupId, int participantId,
        Participant newParticipant)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int groupId</b> - ID of the group that contains participant.<br>
<b>int participantId</b> - ID of the participant to be updated.<br>
<b>Participant newParticipant</b> - Participant object with new params.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/participants/{participanatID}</b> 
and updates information about specific participant.
</td>
</tr>
<tr>
<td>

```java
Participant createParticipantById(int testId, int groupId,
        Participant newParticipant)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired participant.<br/>
<b>int groupId</b> - ID of the group that contains participant.<br>
<b>Participant newParticipant</b> - new Participant object.
</td>
<td>Makes POST request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/participants/</b> 
and creates new participant.
</td>
</tr>
<tr>
<td>

```java
void deleteParticipantById(int testId, int groupId,
        int participantId)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired participant.<br/>
<b>int groupId</b> - ID of the group that contains participant.<br>
<b>int participantId</b> - ID of the participant we wish to delete.
</td>
<td>Makes DELETE request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/participants/{participantsId}</b> 
and deletes participant.
</td>
</tr>
<tr>
<td>

```java
TestRunResult getTestRunResultById
        (int testId, int runId)
```
</td>
<td>
<b>int testId</b> - ID of the test containing info about test runs.<br>
<b>int runId</b> -  ID of the test run.<br>
</td>
<td>Makes GET request to <b>projects/{projectID}/tests/{testID}/runs/{runID}/results/</b>
and retrieves information about <b>all</b> test run results.
</td>
</tr>
<tr>
<td>

```java
TestRunParticipantResult getTestRunParticipantResultById
        (int testId, int runId, int resultId) 
```
</td>
<td>
<b>int testId</b> - ID of the test containing info about test runs.<br>
<b>int runId</b> -  ID of the test run.<br>
<b>int resultId</b> - ID of the specific result.
</td>
<td>Makes GET request to <b>projects/{projectID}/tests/{testID}/runs/{runID}/results/{resultId}/</b>
and retrieves information about <b>specific</b> test run result.
</td>
</tr>
<tr>
<td>

```java
Assert getAssertById
        (int testId, int assertId)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int assertId</b> - ID of the assert that we need.
</td>
<td>Retrieves assert for a specific test.</td>
</tr>
<tr>
<td>

```java
Assert updateAssretById
        (int testId, int assertId, Group newGroup)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int assertId</b> - ID of the assert that is wish to be updated.<br>
<b>Assert newAssert</b> - Assert object with new params.
</td>
<td>Modifies existing assert for a specific test.</td>
</tr>
<tr>
<td>

```java
Assert createNewAssert
        (Assert newAssert, int testId)
```
</td>
<td>
<b>int testId</b>  - ID of the test where assert will be created.<br/>
<b>Assert newAssert</b> - new Assert object.
</td>
<td>Creates new assert for a specific test.</td>
</tr>
<tr>
<td>

```java
void deleteAssertById
        (int testId, int assertId)
```
</td>
<td>
<b>int testId</b>  - ID of the test to be deleted.<br/>
<b>int assertId</b> - ID of the assert to be deleted.
</td>
<td>Deletes assert for a specific test.</td>
</tr>
<tr>
<td>

```java
RunInfo startTestAndPollInfo
        (int testId, int interval, int timeout)
```
</td>
<td>
<b>int testId</b> - ID of the test we wish to start and poll information from.<br>
<b>int interval</b> - Specifying in <b>seconds</b> how often should be method poll for information.<br>
<b>int timeout</b>  - Total amount of time in <b>seconds</b> that should be spending polling information.
</td>
<td>Starts test run by sending POST command to <b>/projects/{projectID}/tests/{testID}/runs/</b>.
After which starts with specified interval within given timeout sending GET request to retrieve information
about test run state. If test run is completed, will return RunInfo object with test run result. 
Also, will give link to results.
</td>
</tr>
</tbody>
</table>

<h3>Usage</h3>
<hr>

<h3>Basic usage</h3>
<p>Getting and updating information about existing test.</p>

```java
// Initiating client through which we will be iterating with Loadero API.
LoaderoClient client = new LoaderoClient(baseUrl, loaderoToken, projectId);

// ID of the test we are interested in.
int testId = 2323;

// Retrieving current test description, if needed.
Test currentTest = client.getTestById(testId);

// Initiating new test options.
String testName = "someName";
int startInterval = 10;
int participantTimeout = 500;
String pathToScript = "path";

Test newTest = new Test("someName", startInterval, participantTimeout,
        TestModeType.LOAD, IncrementStrategy.LINEAR, pathToScript);

// Desired options can be get/set via getters and setters, respectively.
        
// There are three(!) possible ways of setting script content.
// First one is to provide location where script is stored.
// Assumed that script is already fully functional in Loadero environment.        
// This location will be parsed to string. 
// Regardless, if this is .java or .js file.
// Default way used by constructor as well.        
newTest.setScript(URI.create("path/to/.java or .js"));

// The second approach is to pass script as string directly.
newTest.setScript(new String("your script here"));
// The third options is meant to be used to update existing test script template
// for Loadero.
// First we need to define which parameters we wish to set. For this we need to create
// Map<String, String> object, where key is name of the variable and value is the value
// we wish to replace in script.
// Example:        
Map<String, String> newParams = new HashMap<>();
newParams.put("callDuration", "10");
newParams.put("elementTimeout", "30");
newTest.setScript("path to script template", newParams);        

// Now when your test is ready you can actually create it on Loadero side as well!
// Just call
client.createNewTest(newTest);
// You can assign it to variable and use later as well. This will return new test created on Loadero.
Test test = client.createNewTest(newTest);

// After that you can call updateTest() method and store result
// of the operation for later usage if needed.       
Test updatedOptions = client.updateTestById(testId, newTest);

// When you no longer need test just delete it.
client.deleteTestById(testId);
```

<h3>Basic polling usage</h3>

```java
// Another currently popular feature is to poll your test 
// results while running the test itself! And this wrapper can give you just that!

// With method startTestAndPollInfo(testId, interval, timeout) you can start test and...
// you guessed it! Poll the information about the state of the running test!
// When test is done the method will return RunInfo object with
// all the information you need to retrieve results of the test later.        
RunInfo testRunInfo = client.startTestAndPollInfo(testId, 15, 100);

// For example you going to need test run ID field to get results
// about this test run.
int testRunId = testRunInfo.getId();

// Or if you would like to know success rate you can retrieve it with
// getter method respectively.
double successRate = testRunInfo.getSuccessRate();
```

<h3>Getting test run results</h3>

```java
// After successfully run of polling function you can retrieve results of the
// test runs that were made.
// You can use testRunId defined earlier to get all information about test run results
// This will give you a List<SingleTestRunResult> object.
TestRunResult results = client.getTestRunResultById(testId, testRunId);
// This object contains an individual IDs of each test run result that you can
// retrieve later with the next method
TestRunParticipantResult singleResult = client
        .getTestRunParticipantResultById(int testId, int testRunId, int resultId);

// And then, with getters, retrieve all the necessary information about single test run results,
// that you may need.
```

<h3>Unit tests</h3>
<hr>
<p>Package provides some predefined set of unit tests that can be run with Maven.</p>
<p>Unit tests are run using two environment variables called <b>LOADERO_API_TOKEN</b> 
and <b>LOADERO_BASE_URL</b>, respectively. <br>
Set this variables somewhere in your project if you wish to run tests against actual Loadero API service. Otherwise, tests will be run against
mock data on localhost.
</p>

<b>For exmaple:</b>
<p>Use <b>System.getenv()</b> to get values for Loader token and base url from your environment variables<br>
and assign them accordingly inside <b>TestWithWireMock</b> class.</p>

```java
private static final String token = System.getenv("LOADERO_API_TOKEN");
private static final String baseUrl = System.getenv("LOADERO_BASE_URL");
```

<br>
<p>Maven unit tests can be run in to modes.<br>
First one is in the <b>mock</b> mode(default). This mode will run tests only against mocked/saved/predifined data. <br>
Second mode can be activating with <b>-Denv=live</b> flag. In this mode unit tests will be run 
against real Loadero API service using your Loadero API token provided from <b>environment variables</b>.
</p>

<b>To run all tests in /test directory.</b>
```
mvm test
```

<b>To run a specific set of tests against mocked data.</b>
```
mvn -DTest=TestPackage test
```

<b>To run a specific set of tests with real credentials(eg. API toke, real IDs etc.).</b>
```
mvn -DTest=TestPackage -Denv=live test
```

<b>To run a specific test method inside test class.</b>
```
mvn -DTest=TestPackage#testGetTestById test
```