package loadero.main;

import loadero.exceptions.ClientInternalException;
import loadero.model.*;
import loadero.types.LoaderoModelType;
import lombok.Getter;


/**
 * Main entry point of the interaction with the Loadero API.
 * Cannot be extended.
 */
public final class LoaderoClient {
    @Getter
    private final String baseUrl;
    @Getter
    private final int projectId;
    @Getter
    private final String loaderoApiToken;
    private final ServiceFactory serviceFactory;
    
    /**
     * Constructs LoaderoClient object for a specific Loadero project.
     * @param baseUrl        Base url of Loadero API i.e. https://api.loadero.com/v2
     * @param loaderApiToken Loadero API token for your account.
     * @param projectId      ID of the project we want to work with.
     * @throws NullPointerException if baseUrl or loaderoApiToken is null or an empty String.
     * @throws ClientInternalException if projectId is a negative number.
     */
    public LoaderoClient(String baseUrl, String loaderApiToken, int projectId) {
        ClientUtils.checkArgumentsForNull(baseUrl, loaderApiToken);
        ClientUtils.checkIfIntIsNegative(projectId);
        this.baseUrl = baseUrl;
        this.projectId = projectId;
        this.loaderoApiToken = loaderApiToken;
        this.serviceFactory = new ServiceFactory(loaderApiToken, baseUrl, projectId);
    }
    
    /**
     * Returns information about test as Test.
     * @param testId  ID of desired test.
     * @return        Test object.
     */
    public Test getTestById(int testId) {
        return (Test) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS)
                .getById(testId);
    }

    /**
     * Updates the existing Loadero test description based on the
     * parameters provided in new Test() object.
     * @param testId          ID of test to be updated.
     * @param newTestOptions  new Test object with parameters that you wish to change
     * @return                Updated Test
     */
    public Test updateTestById(int testId,
                               Test newTestOptions) {
        TestService testOptionsService = (TestService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        return testOptionsService.updateById(newTestOptions, testId);
    }
    
    /**
     * Creates new test for Loadero.
     * @param newTest - Test object.
     * @return        - Newly created LoaderoTestOption object
     */
    public Test createNewTest(Test newTest) {
        TestService testOptionsService = (TestService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        return testOptionsService.createNewModel(newTest);
    }
    
    /**
     * Deletes test with given ID from Loadero.
     * @param id - ID of the test to be removed.
     */
    public void deleteTestById(int id) {
        TestService service = (TestService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        service.deleteById(id);
    }

    /**
     * Retrieves content of the script file from Loadero API and returns as ScriptDetails object.
     * Use toString() to convert into actual script.
     * @param fileId  ID of the script file
     * @return        ScriptDetails information about script content.
     */
    public ScriptDetails getTestScriptById(int fileId) {
        return (ScriptDetails) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_SCRIPT_FILE_LOC)
                .getById(fileId);
    }

    /**
     * Returns group as Group object from Loadero with specified ID.
     * @param testId  ID of the test.
     * @param groupId  ID of the group.
     * @return    Group object.
     */
    public Group getGroupById(int testId, int groupId) {
        return (Group) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_GROUP)
                .getById(testId, groupId);
    }

    /**
     * Updates Loadero group parameters by given group ID.
     * @param testId     ID of the test containing the group.
     * @param groupId    ID of the group to be updated.
     * @param newGroup   Group object with new parameters.
     * @return           Group object with updated parameters.
     */
    public Group updateGroupById(int testId, int groupId, Group newGroup) {
        GroupService groupService = (GroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        return groupService.updateById(newGroup, testId, groupId);
    }
    
    /**
     * Creates new group for specified Test.
     * @param newGroup New Group object with provided parameters.
     * @param testId   ID of the test for which we would like to create group.
     * @return         Newly created Group object.
     */
    public Group createNewGroup(Group newGroup, int testId) {
        GroupService service = (GroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        return service.createNewModel(newGroup, testId);
    }
    
    /**
     * Deletes specific group from Loadero for specific test.
     * @param testId    ID of the test for which we want to delete group.
     * @param groupId   ID of the group we want to delete.
     */
    public void deleteGroupById(int testId, int groupId) {
        GroupService service = (GroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        
        service.deleteById(testId, groupId);
    }
    
    
    /**
     * Returns participant's information from Loadero as Participant object.
     * @param testId        ID of the test that contains participant.
     * @param groupId       ID of the group containing participant
     * @param participantId desired participant.
     * @return              Participant object
     */
    public Participant getParticipantById(int testId, int groupId,
                                          int participantId) {
        return (Participant) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT)
                .getById(testId, groupId, participantId);
    }

    /**
     * Updates Loadero Participant by it's ID.
     * @param testId               ID of the test that contains participant.
     * @param groupId              ID of the group that contains participant.
     * @param participantId        ID of desired participant.
     * @param newParticipant       Participant object with new params.
     * @return Participant  updated Participant object.
     */
    public Participant updateTestParticipantById(int testId,
                                                 int groupId,
                                                 int participantId,
                                                 Participant newParticipant) {
        ParticipantService participantService = (ParticipantService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT);
        return participantService.updateById(newParticipant, testId, groupId, participantId);
    }
    
    /**
     * Creates new participant for specific group in specific test.
     * @param testId            ID of the test where we want to create participant.
     * @param groupId           ID of the group where we want to create participant.
     * @param newParticipant    Participant object to be created on Loadero site.
     * @return                  Newly created Participant object from Loadero.
     */
    public Participant createParticipantById(int testId, int groupId,
                                             Participant newParticipant) {
        ParticipantService service = (ParticipantService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT);
        return service.createNewModel(newParticipant, testId, groupId);
    }
    
    /**
     * Deletes specific participant.
     * @param testId        ID of the test containing participant.
     * @param groupId       ID of the group containing participant.
     * @param participantId ID of the participant we want to delete.
     */
    public void deleteParticipantById(int testId, int groupId, int participantId) {
        ParticipantService service = (ParticipantService) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT);
        service.deleteById(testId, groupId, participantId);
    }
    
    /**
     * Retrieves assert from Loadero.
     * @param testId    ID of the test where assert is located.
     * @param assertId  ID of an assert.
     * @return          {@link Assert} class.
     */
    public Assert getAssertById(int testId, int assertId) {
        return (Assert) serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_ASSERT)
                .getById(testId, assertId);
    }
    
    /**
     * Creates new assert for a specified test.
     * @param newAssert {@link Assert} class.
     * @param testId    ID of the test where we want new assert.
     * @return          {@link Assert} class.
     */
    public Assert createNewAssert(Assert newAssert, int testId) {
        AssertService service = (AssertService) serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_ASSERT);
        return service.createNewModel(newAssert, testId);
    }
    
    /**
     * Updates existing assert for specific test.
     * @param newAssert  {@link Assert} class with parameters we wish to replace.
     * @param testId     ID of the test.
     * @param assertId   ID of the assert.
     * @return          {@link Assert} class with updated parameters.
     */
    public Assert updateAssertById(Assert newAssert, int testId, int assertId) {
        AssertService service = (AssertService) serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_ASSERT);
        return service.updateById(newAssert, testId, assertId);
    }
    
    /**
     * Delete existing assert for specific test.
     * @param testId    ID of the test.
     * @param assertId  ID of the assert.
     */
    public void deleteAssertById(int testId, int assertId) {
        AssertService service = (AssertService) serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_ASSERT);
        service.deleteById(testId, assertId);
    }
    
    /**
     * Gets information about all test run results from Loadero API.
     * @param testId  ID of the desired test to get results from.
     * @param runId   ID of the test run.
     * @return        TestRunResult object, that contains list of LoaderoSingleTestRunResult objects.
     */
    public TestRunResult getTestRunResultById(int testId, int runId) {
        return (TestRunResult) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_RUN_RESULT)
                .getById(testId, runId);
    }

    /**
     * Gets information about specific results.
     * @param testId     ID of the test we wish to get results.
     * @param runId      ID of the test run.
     * @param resultId   ID of the result.
     * @return           TestRunParticipantResult object containing information such as ID, status,
     *                    selenium_status, log paths, asserts and artifacts.
     */
    public TestRunParticipantResult getTestRunParticipantResultById(int testId,
                                                                    int runId,
                                                                    int resultId) {
        return (TestRunParticipantResult) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_TEST_RUN_PARTICIPANT_RESULT)
                .getById(testId, runId, resultId);
    }
    
    /**
     * Returns Loadero statics provided by API.
     * @return Returns class instance of {@link Statics}
     */
    public Statics getLoaderoStatics() {
        return (Statics) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_STATICS).getById();
    }
    
    /**
     * Returns metric paths used by Loadero for asserts.
     * @return  {@link MetricPaths} class with webrtc and machine paths separated.
     */
    public MetricPaths getMetricPaths() {
        StaticsService service = (StaticsService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_STATICS);
        return service.getMetricPaths();
    }

    /**
     * Start test run by sending POST command underneath to tests/testId/runs endpoint.
     * After which actively starts polling for information about test run.
     * Returns run info when test is done or time of the polling runs out.
     * @param testId    ID of the test that is going to run.
     * @param interval  how often check for information. In seconds.
     * @param timeout   how long should polling for information. In seconds.
     * @throws ClientInternalException if interval is less than 5 seconds.
     * @return          RunInfo containing information about test run.
     */
    public RunInfo startTestAndPollInfo(int testId, int interval, int timeout) {
        if (interval < 5)
            throw new ClientInternalException("Interval is too short. Should be at least 5 seconds.");
        
        
        PollingService pollingService = (PollingService) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_RUN_INFO);
        return pollingService.startTestAndPoll(testId, interval, timeout);
    }
}

