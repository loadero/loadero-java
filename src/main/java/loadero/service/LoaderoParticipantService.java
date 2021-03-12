package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoParticipant;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoParticipant object.
 */
public class LoaderoParticipantService extends AbstractLoaderoService
        implements LoaderoSpecialOperation<LoaderoParticipant> {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder         = super.getUrlBuilder();

    public LoaderoParticipantService(LoaderoCrudController crudController,
                                     LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoParticipant getById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];
        LoaderoClientUtils.checkArgumentsForNull(testId, groupId, participantId);

        String particUrl = buildUrl(testId, groupId, participantId);
        return (LoaderoParticipant) crudController.get(
                particUrl, LoaderoModelType.LOADERO_PARTICIPANT
        );
    }

    @Override
    public LoaderoParticipant updateById(LoaderoParticipant newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];

        LoaderoClientUtils.checkArgumentsForNull(newModel, testId, groupId, participantId);

        String participantUrl = buildUrl(testId, groupId, participantId);
        LoaderoParticipant currentParticInfo = getById(testId, groupId, participantId);
        LoaderoModel updatedParticipant = LoaderoClientUtils
                .copyUncommonFields(
                        currentParticInfo,
                        newModel,
                        LoaderoModelType.LOADERO_PARTICIPANT);

        return (LoaderoParticipant) crudController
                .update(participantUrl, LoaderoModelType.LOADERO_PARTICIPANT, updatedParticipant);
    }
    
    @Override
    public LoaderoParticipant createNewModel(LoaderoParticipant newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        String url = urlBuilder.buildGroupURL(testId, groupId) + "/participants/";
        return (LoaderoParticipant) crudController
                .post(url, LoaderoModelType.LOADERO_PARTICIPANT, newModel);
    }
    
    @Override
    public void deleteById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];
        
        crudController.delete(buildUrl(testId, groupId, participantId));
    }
    
    @Override
    public String buildUrl(int... ids) {
        return String.format("%s/", urlBuilder.buildParticipantURL(ids[0], ids[1], ids[2]));
    }
}
