package loadero.main;

import loadero.model.LoaderoModel;
import loadero.model.Participant;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to Participant object.
 */
final class ParticipantService extends AbstractService
        implements SpecialOperations<Participant> {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder         = super.getUrlBuilder();

    public ParticipantService(CrudController crudController,
                              CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public Participant getById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];
        ClientUtils.checkIfIntIsNegative(testId, groupId, participantId);

        String particUrl = buildUrl(testId, groupId, participantId);
        return (Participant) crudController.get(
                particUrl, LoaderoModelType.LOADERO_PARTICIPANT
        );
    }

    @Override
    public Participant updateById(Participant newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];

        ClientUtils.checkArgumentsForNull(newModel);
        ClientUtils.checkIfIntIsNegative(testId, groupId, participantId);
        
        String participantUrl = buildUrl(testId, groupId, participantId);
        Participant currentParticInfo = getById(testId, groupId, participantId);
        LoaderoModel updatedParticipant = ClientUtils
                .copyUncommonFields(
                        currentParticInfo,
                        newModel);

        return (Participant) crudController
                .update(participantUrl, LoaderoModelType.LOADERO_PARTICIPANT, updatedParticipant);
    }
    
    @Override
    public Participant createNewModel(Participant newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        ClientUtils.checkArgumentsForNull(newModel);
        ClientUtils.checkIfIntIsNegative(testId, groupId);
        
        String url = urlBuilder.buildGroupURL(testId, groupId) + "/participants/";
        return (Participant) crudController
                .post(url, LoaderoModelType.LOADERO_PARTICIPANT, newModel);
    }
    
    @Override
    public void deleteById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        int participantId = ids[2];
        ClientUtils.checkIfIntIsNegative(testId, groupId, participantId);
        crudController.delete(buildUrl(testId, groupId, participantId));
    }
    
    @Override
    public String buildUrl(int... ids) {
        return String.format("%s/", urlBuilder.buildParticipantURL(ids[0], ids[1], ids[2]));
    }
}
