<h2>Java client library for Loadero</h2>

<h3>Description</h3>
<hr>
<p>This is Java library that can be used as a client to interact with <a href="https://loadero.com/home">Loadero</a> API service.</p>
<p>
It allows you to create, delete, update, launch and retrieve information about tests 
and test's related information.
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
enum Loadero(){}
```
</td>
<td>Singleton configuration enum for Loadero.
Here you can define things such as what API token to use and for which project.</td>
</tr>
<tr>
<td>

```java
class Test(){}
```
</td>
<td>
Used to perform CRUD operations on tests.
</td>
</tr>
<tr>
<td>

```java
class Group(){}
```
</td>
<td>Used to perform CRUD operations on groups.</td>
</tr>
<tr>
<td>

```java
class Participant(){}
```
</td>
<td>Used to perform CRUD operations on participants.</td>
</tr>
<tr>
<td>

```java
class TestRun(){}
```
</td>
<td>Used to perform poll operation on already running test to get 
information about its test run state.</td>
</tr>
<tr>
<td>

```java
class Script(){}
```
</td>
<td>Used to represent information about script file that is used
by Loadero to run tests.
</td>
</tr>
<tr>
<td>

```java
class Result(){}
```
</td>
<td>Used to get information about Loadero test's run result.</td>
</tr>
<tr>
<td>

```java
class Browser(){}
```
</td>
<td>Used to define different versions of browsers.</td>
</tr>
<tr>
<td>

```java
class Assert(){}
```
</td>
<td>Used to perform CRUD operations on asserts.</td>
</tr>
<tr>
<td>

```java
class Precondition(){}
```
</td>
<td>Used to perform CRUD operations on assert's preconditions.
However, this feature availability depends on your subscription plan.</td>
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
enum BrowserLatest
```
</td>
<td>
Contains latest versions for Chrome and Firefox browsers.
</td>
</tr>
<tr>
<td>

```java
enum ComputeUnit
```
</td>
<td>
Contains compute unit options. Availability depends on the subscription plan.
</td>
</tr>
<tr>
<td>

```java
enum IncrementStrategy
```
</td>
<td>
Contains options for increment strategies.
</td>
</tr>
<tr>
<td>

```java
enum Location
```
</td>
<td>
Contains different geo-locations.
Availability depends on the subscription plan.
</td>
</tr>
<tr>
<td>

```java
enum MediaType
```
</td>
<td>
Contains media type options.
</td>
</tr>
<tr>
<td>

```java
enum Network
```
</td>
<td>
Contains network condition options.
</td>
</tr>
<tr>
<td>

```java
enum TestMode
```
</td>
<td>
Contains options for setting testing mode.
Availability depends on the subscription plan.
</td>
</tr>
<tr>
<td>

```java
enum MachineAsserts
```
</td>
<td>
Contains options for all machine asserts paths.
</td>
</tr>
<tr>
<td>

```java
enum WebrtcAsserts
```
</td>
<td>
Contains options for all WebRTC asserts paths.
</td>
</tr>
<tr>
<td>

```java
enum AssertOperator
```
</td>
<td>
Contains options for asserts operators.
</td>
</tr>
<tr>
<td>

```java
enum RunStatus
```
</td>
<td>
Contains options for test's run statuses.
</td>
</tr>
<tr>
<td>

```java
enum Property
```
</td>
<td>
Contains options for precondition's properties.
</td>
</tr>
</tbody>
</table>

<hr>

<h4>Setup</h4>
```java
// Before beginning to have fun with tests you should
// initiate client instance configuration that is going to be used for performing different operations.
Loadero.init(apiToken, projectId);
```
<h4>Retrieve existing item</h4>
<p>All items can be retrieved with their respective <b>IDs</b>.</p>

```java
int testId = 2323;
int groupId = 13123;
int participantId = 1231231;

// Retrieving current test description, if needed.
    
Test test = Test.read(testId);
Group group = Group.read(testId, groupId);
Participant participant = Participant.read(testId, groupId, participantId);
// and so on...
```

<h4>Create new item</h4>
<p>Each model has its own respective <b>*Params</b> class that is used for
creation or modification of the model.</p>

<p><b>Disclaimer:</b> 
There are two different setters for a test's script. Each should be used accordingly with your
Loadero's project language.</p>
<ol>
    <li>
    For a <b>Java/TestUI</b> project you should use <code>.withScript(pathToScript, "nameOfTheTestMethod")</code>.
    This will parse specified <b>.java</b> file and extract test method annotated with <b>@Test</b>
    and this test method will be used to run tests.<br> 
    </li>
    <li>
    For a <b>JavaScript/Nigthwatch.js and Python/Py-TestUI</b> project you can use <code>.withScript(pathToScript)</code>. 
    This will simply parse specified file into string.
    </li>
</ol>

<b>Disclaimer:</b> You can use <code>.withScript(pathToScript)</code> method to set Java/TestUI scripts as well, 
but then script should be written in <b>.txt</b> file and in the same way as shown
in Loadero's <a href="https://wiki.loadero.com/testui-java/script-examples/">wiki page</a>.

```java
TestParams tParams = TestParams
    .builder()
    .withName("Test 1")
    .withMode(TestMode.LOAD)
    .withIncrementStrategy(IncrementStrategy.RANDOM)
    .withStartInterval(Duration.ofSeconds(10))
    .withParticipantTimeout(Duration.ofSeocnds(360))
    .withScript("/path/to/TestMe.java/", "testSomething")
    .build();

Test newTest = Test.create(tParams);

GroupParams gParams = GroupParams
    .builder()
    .withTestId(newTest.getId()) // we need to provide id of existing test in order to create a new group for it
    .withName("group name")
    .withCount(1) 
    .build();

Group newGroup = Group.create(gParams);

ParticipantParams pParams = ParticipantParams
    .builder()
    .withTestId(newTest.getId())
    .withGroupId(newGroup.getId())
    .withName("participant1")
    .withCount(1)
    .withLocation(Location.EU_WEST_1)
    .withNetwork(Network.DEFAULT)
    .withBrowser(new Browser(BrowserLatest.CHROME_LATEST))
    .withComputeUnit(ComputeUnit.G2)
    .withMediaType(MediaType.DEFAULT)
    .withRecordAudio(false)
    .build();

Participant newParticipant = Participant.create(pParams);
// and so on for other items...
```

<h4>Update existing item</h4>
<p>When you want to modify an existing object you must not forget to
specify the respective <b>IDs</b> of that object.</p>

```java
// Test update
TestParams tParams = TestParams
    .builder()
    .withId(testId)
    .withName("New name") // Given new name to a test
    .build();

Test updatedTest = Test.update(tParams);

// Group update
GroupParams gParams = GroupParams
    .builder()
    .withId(groupId)
    .withTestId(updatedTest.getId())
    .withCount(10)
    .build();

Group updatedGroup = Group.update(gParams);

// Participant update
ParticipantParams pParams = ParticipantParams
    .builder()
    .withId(participantId)
    .withTestId(updatedTest.getId())
    .withGroupId(updatedGroup.getId())
    .withName("new name")
    .build();

Participant updatedParticipant = Participant.update(pParams)
```

<h4>Delete existing item</h4>
<p>Delete operation doesn't return anything.</p>

```java
Test.delete(testId);
Group.delete(testId, groupId);
Participant.delete(testId, groupId, participantId);
// and so on...
```

<h3>Launch tests and get results</h3>
<p><b>Test</b> class is closely associated with <b>TestRun</b> class in a 
sense that you can "launch a test to get test run results".
Based on that here is how one can do that.</p>

<p><b>Disclaimer:</b> You can launch only existing tests.</p>

<p>There are two way of how you can poll for results.</p>
<ol>
    <li>
    <code>.poll(testId, runId, interval, timeout)</code> method can be used when
    you have time constraints on how long should polling be running. 
    This will run polling function for the specify period of time.
    </li>
    <li>
    <code>.poll(testId, runId, interval)</code > method is similar to the first one except,
    that here <b>timeout</b> argument by default is set to <b>12 hours</b>. If the test run exceeds 12 hours time period, 
    then <b>ApiPollingException</b> will be thrown.
    This should be used with caution. For more information refer to <a>https://wiki.loadero.com/loadero-usage/test-run-queue/</a>.
    </li>
</ol>

<p>When the test run is <b>done</b> it will return you <b>TestRun</b> 
object with <b>status</b> field marked as <b>done</b> and some additional information such as
<b>resultId</b> that can be used to retrieve test run results.
</p>

```java
TestRun launch = Test.launch(testId);
// Third parameter is specifying how often it should check on test run.
// In this case it will log update message every 30 seconds.    
TestRun run = TestRun.poll(testId, launch.getRunId(), Duration.ofSeconds(30));

// Assuming that test run was successful
Result result = Result.read(run.getId(), run.getResultId());    

// Test run can be gracefully aborted.
// In such case polling will be aborted as well as there is no longer active test run   
TestRun.stop(testId, rundId);
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
