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
<table>
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
<table>
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
public LoaderoTestOptions getTestOptions()
```
</td>
<td>
Makes GET request to <b>/projects/{projectID}/tests/{testID}</b> endpoint and
retrieves information about existing test. Takes no arguments. Returns data as LoaderoTestOptions object.</td>
</tr>
<tr>
<td>

```java
public LoaderoTestOptions updateTestOptions
        (LoaderoTestOptions newOptions)
```
</td>
<td>Makes PUT request to <b>/projects/{projectID}/tests/{testID}</b> and updates existing test options 
in Loadero. 
Takes in LoaderoTestOptions object with desired params set through setter methods.
</td>
</tr>
<tr>
<td>

```java
public String getFileScriptConent(String id)
```
</td>
<td>Makes GET request to <b>/projects/{projectID}/files/{fileID}/</b> and retrieves the content
of the script used for testing.
</td>
</tr>
<tr>
<td>

```java
public LoaderoGroup getGroupById(String id)
```
</td>
<td>Makes GET request to <b>/projects/{projectID}/tests/{testID}/groups/{groupID}/</b> 
and retrieves information about group.
</td>
</tr>
<tr>
<td>

```java
public LoaderoGroup getParticipantById(String groupId)
```
</td>
<td>Makes GET request to <b> /projects/{projectID}/tests/{testID}/participants/{participantID}/</b> 
and retrieves information about participant.
</td>
</tr>
<tr>
<td>

```java
public LoaderoModel startTestAndPollInfi(int interval,
        int timeout)
```
</td>
<td>Starts test run by sending POST command to <b>/projects/{projectID}/tests/{testID}/runs/</b>.
After which starts with specified interval within given timeout sending GET request to retrieve information
about test run state. If test run is completed, will return LoaderoModel object with test run result. 
Also, will give link to results.
</td>
</tr>
</tbody>
</table>