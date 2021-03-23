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
interface LoaderoModel(){}
```
</td>
<td>Marker interface to group similar classes.</td>
</tr>
<tr>
<td>

```java
class LoaderoGroup(){}
```
</td>
<td>Class that is responsible to represent information about Loadero group.</td>
</tr>
<tr>
<td>

```java
class LoaderoTestOptions(){}
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
class LoaderoRunInfo(){}
```
</td>
<td>Class that is used to collect information about test runs.</td>
</tr>
<tr>
<td>

```java
class LoaderoParticipant(){}
```
</td>
<td>Class that represents information about Loadero participant.</td>
</tr>
<tr>
<td>

```java
class LoaderoScriptFileLoc(){}
```
</td>
<td>Class that is represent information about script file that is used
by Loadero to run tests against.
</td>
</tr>
<tr>
<td>

```java
class LoaderoTestResults(){}
```
</td>
<td>Class that (will) represent information about Loadero test's results.</td>
</tr>
<tr>
<td>

```java
class LoaderoModelFactory(){}
```
</td>
<td>Factory that is used to create concrete classes of LoaderoModel interface.</td>
</tr>
<tr>
<td>

```java
class LoaderoTestRunResult(){}
```
</td>
<td>Class that is responsible for storing information about all test run results.</td>
</tr>
<tr>
<td>

```java
class LoaderoTestRunParticipantResult(){}
```
</td>
<td>Class that is responsible to represent information about single participant test run result.</td>
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
enum LoaderoBrowserType
```
</td>
<td>
Contains different browser types that can be used for testing.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoComputeUnitsType
```
</td>
<td>
Contains compute units options.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoIncrementStrategyType
```
</td>
<td>
Contains possible options for increment strategies.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoLocationType
```
</td>
<td>
Contains different geo locations that are available for testing.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoMediaType
```
</td>
<td>
Contains media options that can be used for testing.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoNetworkTypes
```
</td>
<td>
Contains different network conditions that can be set for testing.
</td>
</tr>
<tr>
<td>

```java
enum LoaderoTestModeType
```
</td>
<td>
Contains options for setting testing mode.
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
LoaderoTestOptions getTestOptionsById(int testId)
```
</td>
<td>
<b>int testId</b> - ID of the test we would like to get.
</td>
<td>
Makes GET request to <b>/projects/{projectID}/tests/{testID}</b> endpoint and
retrieves information about existing test. Takes no arguments. Returns data as LoaderoTestOptions object.</td>
</tr>
<tr>
<td>

```java
LoaderoTestOptions updateTestOptionsById
        (int testId, LoaderoTestOptions newOptions)
```
</td>
<td>
<b>LoaderoTestOptions newOptions</b> - Required parameter that is used to set new test options
in Loadero via API call.

<b>int testId</b> - ID of the test we wish to update.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}</b> and updates existing test options 
in Loadero. 
Takes in LoaderoTestOptions object with desired params set through setter methods.
</td>
</tr>
<tr>
<td>

```java
LoaderoTestOptions createNewTest
        (LoaderoTestOptions newTest) 
```
</td>
<td>
<b>LoaderoTestOptions newTest</b> - Required parameter that is used to create new test in Loadero.
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
LoaderoScriptFileLoc getTestScriptById(int fileId)
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
LoaderoGroup getGroupById
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
LoaderoGroup updateGroupById
        (int testId, int groupId, LoaderoGroup newGroup)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int groupId</b> - ID of the group that is wish to be updated.<br>
<b>LoaderoGroup newGroup</b> - LoaderoGroup object with new params.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/</b> 
and updates information about group.
</td>
</tr>
<tr>
<td>

```java
LoaderoGroup createNewGroup
        (LoaderoGroup newGroup, int testId)
```
</td>
<td>
<b>int testId</b>  - ID of the test where group will be created.<br/>
<b>LoaderoGroup newGroup</b> - new LoaderoGroup object.
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
LoaderoParticipant getParticipantById
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
LoaderoParticipant updateTestParticipantById
        (int testId, int groupId, int participantId,
        LoaderoParticipant newParticipant)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired group.<br/>
<b>int groupId</b> - ID of the group that contains participant.<br>
<b>int participantId</b> - ID of the participant to be updated.<br>
<b>LoaderoParticipant newParticipant</b> - LoaderoParticipant object with new params.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/participants/{participanatID}</b> 
and updates information about specific participant.
</td>
</tr>
<tr>
<td>

```java
LoaderoParticipant createParticipantById(int testId, int groupId,
        LoaderoParticipant newParticipant)
```
</td>
<td>
<b>int testId</b>  - ID of the test that contains desired participant.<br/>
<b>int groupId</b> - ID of the group that contains participant.<br>
<b>LoaderoParticipant newParticipant</b> - new LoaderoParticipant object.
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
LoaderoTestRunResult getTestRunResultById
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
LoaderoTestRunParticipantResult getTestRunParticipantResultById
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
LoaderoRunInfo startTestAndPollInfo
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
about test run state. If test run is completed, will return LoaderoRunInfo object with test run result. 
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
LoaderoTestOptions currentTestOptions = client.getTestOptionsById(testId);

// Initiating new test options.
String testName = "someName";
int startInterval = 10;
int participantTimeout = 500;
String pathToScript = "path";

LoaderoTestOptions newTestOptions = new LoaderoTestOptions(
        "someName", startInterval, participantTimeout,
        LoaderoTestModeType.LOAD, LoaderoIncrementStrategy.LINEAR, pathToScript);

// Desired options can be get/set via getters and setters, respectively.
        
// There are three(!) possible ways of setting script content.
// First one is to provide location where script is stored.
// Assumed that script is already fully functional in Loadero environment.        
// This location will be parsed to string. 
// Regardless, if this is .java or .js file.
// Default way used by constructor as well.        
newTestOptions.setScript(URI.create("path/to/.java or .js"));

// The second approach is to pass script as string directly.
newTestOptions.setScript(new String("your script here"));
// The third options is meant to be used to update existing test script template
// for Loadero.
// First we need to define which parameters we wish to set. For this we need to create
// Map<String, String> object, where key is name of the variable and value is the value
// we wish to replace in script.
// Example:        
Map<String, String> newParams = new HashMap<>();
newParams.put("callDuration", "10");
newParams.put("elementTimeout", "30");
newTestOptions.setScript("path to script template", newParams);        

// Now when your test is ready you can actually create it on Loadero side as well!
// Just call
client.createNewTest(newTestOptions);
// You can assign it to variable and use later as well. This will return new test created on Loadero.
LoaderoTestOptions test = client.createNewTest(newTestOptions);

// After that you can call updateTestOptions() method and store result
// of the operation for later usage if needed.       
LoaderoTestOptions updatedOptions = client.updateTestOptionsById(testId, newTestOptions);

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
LoaderoRunInfo testRunInfo = client.startTestAndPollInfo(testId, 15, 100);

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
// This will give you a List<LoaderoSingleTestRunResult> object.
LoaderoTestRunResult results = client.getTestRunResultById(testId, testRunId);
// This object contains an individual IDs of each test run result that you can
// retrieve later with the next method
LoaderoTestRunParticipantResult singleResult = client
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
mvn -DTest=TestLoaderoPackage test
```

<b>To run a specific set of tests with real credentials(eg. API toke, real IDs etc.).</b>
```
mvn -DTest=TestLoaderoPackage -Denv=live test
```

<b>To run a specific test method inside test class.</b>
```
mvn -DTest=TestLoaderoPackage#testGetTestOptionsById test
```