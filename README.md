<h2>Java wrapper for Loadero API</h2>

<h3>Description</h3>
<hr>
<p>This is a small java library that can be used as a wrapper to interact with Loadero API.</p>
<p>For now only a small portion of functionality is covered such as getting or updating information about 
existing tests, updating information about Loadero group and participants.
</p>

<h3>Plans</h3>
<hr>
<p>Full feature CRUD application to interact with Loadero API service.</p>

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
 LoaderoTestOptions getTestOptions()
```
</td>
<td>
-
</td>
<td>
Makes GET request to <b>/projects/{projectID}/tests/{testID}</b> endpoint and
retrieves information about existing test. Takes no arguments. Returns data as LoaderoTestOptions object.</td>
</tr>
<tr>
<td>

```java
 LoaderoTestOptions updateTestOptions
        (LoaderoTestOptions newOptions)
```
</td>
<td>
<b>LoaderoTestOptions newOptions</b> - Required parameter that is used to set new test options
in Loadero via API call.
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}</b> and updates existing test options 
in Loadero. 
Takes in LoaderoTestOptions object with desired params set through setter methods.
</td>
</tr>
<tr>
<td>

```java
 String getFileScriptConent(String id)
```
</td>
<td>
<b>String id</b> - ID that is pointing to the script file on Loadero.
</td>
<td>Makes GET request to <b>/projects/{projectID}/files/{fileID}/</b> and retrieves the content
of the script used for testing.
</td>
</tr>
<tr>
<td>

```java
 LoaderoGroup getGroupById(String id)
```
</td>
<td>
<b>String id</b> - ID of the group that is used to retrieve information about Loadero Group.
</td>
<td>Makes GET request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/</b> 
and retrieves information about group.
</td>
</tr>
<tr>
<td>

```java
 LoaderoGroup getParticipantById(String groupId)
```
</td>
<td>
<b>String id</b> - ID of the participant that is used to retrieve information.
</td>
<td>Makes GET request to <b> /projects/{projectID}/tests/{testID}/participants/{participantID}/</b> 
and retrieves information about participant.
</td>
</tr>
<tr>
<td>

```java
  LoaderoModel startTestAndPollInfo
        (int interval, int timeout)
```
</td>
<td>
<b>int interval</b> - Specifying how often should be method poll for information. In seconds.

<b>int timeout</b>  - Total amount of time that should be spending polling information. In seconds.
</td>
<td>Starts test run by sending POST command to <b>/projects/{projectID}/tests/{testID}/runs/</b>.
After which starts with specified interval within given timeout sending GET request to retrieve information
about test run state. If test run is completed, will return LoaderoModel object with test run result. 
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
LoaderoClient client = new LoaderoClient(loaderoToken, projectId, testId);

// Retrieving current test description, if needed.
LaoderoTestOptions currentTestOptions = client.getTestOptions();

// Initiating new test options.
// New options are...well...optional. Those options that wasn't specified
// won't be substitute.
LoaderoTestOptions newTestOptions = new LoaderoTestOptions();

// Desired options can be get/set via getters and setters, respectively.
        
// There is two possibilities of setting script content.
// First one is to provide path to location where script is stored.
// This location will be parsed to string. 
// Regardless, if this is .java or .js file.
newTestOptions.setName("New name 1");
newTestOptions.setMode("performance");
newTestOptions.setScript(URI.create("path/to/.java or .js"));

// The second approach is to pass script as string directly.
newTestOptions.setScript(new String("your script here"));

// After that you can call updateTestOptions() method and store result
// of the operation for later usage if needed.       
LoaderoTestOptions updatedOptions = (LoaderoTestOptions) client.updateTestOptions(newTestOptions);
```

<h3>Basic polling usage</h3>

```java
// Another currently popular feature is to poll your test 
// results while running the test itself!
// And this wrapper can give you just that!
        
// With method startTestAndPollInfo(interval, timeout) you can start test and...
// you guessed it! Poll the information about the state of the running test!
// When test is done the method will return LoaderoRunInfo object with
// all the information you need to retrieve results of the test later.        
LoaderoRunInfo testRunInfo = (LoaderoRunInfo) client.startTestAndPollInfo(15, 100);
```

<h3>Unit tests</h3>
<p>Package provides some predefined set of unit tests that can be run with Maven.</p>

<b>To run all tests in /test directory.</b>
```
mvm test
```

<b>To run a specific set of tests.</b>
```
mvn -DTest=TestWithWiremock test
```

<b>To run a specific test method inside test class.</b>
```
mvn -DTest=TestWithWiremock#getTestOptions test
```