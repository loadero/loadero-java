package loadero.main;

import loadero.exceptions.LoaderoException;
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
    private final LoaderoServiceFactory serviceFactory;
    
    /**
     * Constructs LoaderoClient object for a specific Loadero project.
     * @param baseUrl        Base url of Loadero API i.e. https://api.loadero.com/v2
     * @param loaderApiToken Loadero API token for your account.
     * @param projectId      ID of the project we want to work with.
     * @throws NullPointerException if baseUrl or loaderoApiToken is null or an empty String.
     * @throws LoaderoException if projectId is a negative number.
     */
    public LoaderoClient(String baseUrl, String loaderApiToken, int projectId) {
        LoaderoClientUtils.checkArgumentsForNull(baseUrl, loaderApiToken);
        LoaderoClientUtils.checkIfIntIsNegative(projectId);
        this.baseUrl = baseUrl;
        this.projectId = projectId;
        this.loaderoApiToken = loaderApiToken;
        this.serviceFactory = new LoaderoServiceFactory(loaderApiToken, baseUrl, projectId);
    }
    
    /**
     * Returns information about test as LoaderoTestOptions.
     * @param testId  ID of desired test.
     * @return        LoaderoTestOptions object.
     */
    public LoaderoTestOptions getTestOptionsById(int testId) {
        return (LoaderoTestOptions) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS)
                .getById(testId);
    }

    /**
     * Updates the existing Loadero test description based on the
     * parameters provided in new LoaderoTestOptions() object.
     * @param testId          ID of test to be updated.
     * @param newTestOptions  new LoaderoTestOptions object with parameters that you wish to change
     * @return                Updated LoaderoTestOptions
     */
    public LoaderoTestOptions updateTestOptionsById(int testId,
                                                LoaderoTestOptions newTestOptions) {
        LoaderoTestOptionsService testOptionsService = (LoaderoTestOptionsService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        return testOptionsService.updateById(newTestOptions, testId);
    }
    
    /**
     * Creates new test for Loadero.
     * @param newTest - LoaderoTestOptions object.
     * @return        - Newly created LoaderoTestOption object
     */
    public LoaderoTestOptions createNewTest(LoaderoTestOptions newTest) {
        LoaderoTestOptionsService testOptionsService = (LoaderoTestOptionsService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        return testOptionsService.createNewModel(newTest);
    }
    
    /**
     * Deletes test with given ID from Loadero.
     * @param id - ID of the test to be removed.
     */
    public void deleteTestById(int id) {
        LoaderoTestOptionsService service = (LoaderoTestOptionsService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_TEST_OPTIONS);
        service.deleteById(id);
    }

    /**
     * Retrieves content of the script file from Loadero API and returns as LoaderoScriptFileLoc object.
     * Use toString() to convert into actual script.
     * @param fileId  ID of the script file
     * @return        LoaderoScriptFileLoc information about script content.
     */
    public LoaderoScriptFileLoc getTestScriptById(int fileId) {
        return (LoaderoScriptFileLoc) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_SCRIPT_FILE_LOC)
                .getById(fileId);
    }

    /**
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param testId  ID of the test.
     * @param groupId  ID of the group.
     * @return    LoaderoGroup object.
     */
    public LoaderoGroup getGroupById(int testId, int groupId) {
        return (LoaderoGroup) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_GROUP)
                .getById(testId, groupId);
    }

    /**
     * Updates Loadero group parameters by given group ID.
     * @param testId     ID of the test containing the group.
     * @param groupId    ID of the group to be updated.
     * @param newGroup   LoaderoGroup object with new parameters.
     * @return           LoaderoGroup object with updated parameters.
     */
    public LoaderoGroup updateGroupById(int testId, int groupId, LoaderoGroup newGroup) {
        LoaderoGroupService groupService = (LoaderoGroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        return groupService.updateById(newGroup, testId, groupId);
    }
    
    /**
     * Creates new group for specified Test.
     * @param newGroup New LoaderoGroup object with provided parameters.
     * @param testId   ID of the test for which we would like to create group.
     * @return         Newly created LoaderoGroup object.
     */
    public LoaderoGroup createNewGroup(LoaderoGroup newGroup, int testId) {
        LoaderoGroupService service = (LoaderoGroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        return service.createNewModel(newGroup, testId);
    }
    
    /**
     * Deletes specific group from Loadero for specific test.
     * @param testId    ID of the test for which we want to delete group.
     * @param groupId   ID of the group we want to delete.
     */
    public void deleteGroupById(int testId, int groupId) {
        LoaderoGroupService service = (LoaderoGroupService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_GROUP);
        
        service.deleteById(testId, groupId);
    }
    
    
    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * @param testId        ID of the test that contains participant.
     * @param groupId       ID of the group containing participant
     * @param participantId desired participant.
     * @return              LoaderoParticipant object
     */
    public LoaderoParticipant getParticipantById(int testId, int groupId,
                                                 int participantId) {
        return (LoaderoParticipant) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT)
                .getById(testId, groupId, participantId);
    }

    /**
     * Updates Loadero Participant by it's ID.
     * @param testId               ID of the test that contains participant.
     * @param groupId              ID of the group that contains participant.
     * @param participantId        ID of desired participant.
     * @param newParticipant       LoaderoParticipant object with new params.
     * @return LoaderoParticipant  updated LoaderoParticipant object.
     */
    public LoaderoParticipant updateTestParticipantById(int testId,
                                                        int groupId,
                                                        int participantId,
                                                        LoaderoParticipant newParticipant) {
        LoaderoParticipantService participantService = (LoaderoParticipantService)
                serviceFactory.getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT);
        return participantService.updateById(newParticipant, testId, groupId, participantId);
    }
    
    /**
     * Creates new participant for specific group in specific test.
     * @param testId            ID of the test where we want to create participant.
     * @param groupId           ID of the group where we want to create participant.
     * @param newParticipant    LoaderoParticipant object to be created on Loadero site.
     * @return                  Newly created LoaderoParticipant object from Loadero.
     */
    public LoaderoParticipant createParticipantById(int testId, int groupId,
                                                    LoaderoParticipant newParticipant) {
        LoaderoParticipantService service = (LoaderoParticipantService)
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
        LoaderoParticipantService service = (LoaderoParticipantService) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_PARTICIPANT);
        service.deleteById(testId, groupId, participantId);
    }
    
    /**
     * Gets information about all test run results from Loadero API.
     * @param testId  ID of the desired test to get results from.
     * @param runId   ID of the test run.
     * @return        LoaderoAllTestRunResults object, that contains list of LoaderoSingleTestRunResult objects.
     */
    public LoaderoTestRunResult getTestRunResultById(int testId, int runId) {
        return (LoaderoTestRunResult) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_RUN_RESULT)
                .getById(testId, runId);
    }

    /**
     * Gets information about specific results.
     * @param testId     ID of the test we wish to get results.
     * @param runId      ID of the test run.
     * @param resultId   ID of the result.
     * @return           LoaderoSingleTestRunResult object containing information such as ID, status,
     *                    selenium_status, log paths, asserts and artifacts.
     */
    public LoaderoTestRunParticipantResult getTestRunParticipantResultById(int testId,
                                                                       int runId,
                                                                       int resultId) {
        return (LoaderoTestRunParticipantResult) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_TEST_RUN_PARTICIPANT_RESULT)
                .getById(testId, runId, resultId);
    }

    /**
     * Start test run by sending POST command underneath to tests/testId/runs endpoint.
     * After which actively starts polling for information about test run.
     * Returns run info when test is done or time of the polling runs out.
     * @param testId    ID of the test that is going to run.
     * @param interval  how often check for information. In seconds.
     * @param timeout   how long should polling for information. In seconds.
     * @throws LoaderoException if interval is less than 5 seconds.
     * @return          LoaderoRunInfo containing information about test run.
     */
    public LoaderoRunInfo startTestAndPollInfo(int testId, int interval, int timeout) {
        if (interval < 5) {
            throw new LoaderoException("Interval is too short. Should be at least 5 seconds.");
        }
        
        LoaderoPollingService pollingService = (LoaderoPollingService) serviceFactory
                .getLoaderoService(LoaderoModelType.LOADERO_RUN_INFO);
        return pollingService.startTestAndPoll(testId, interval, timeout);
    }
}

