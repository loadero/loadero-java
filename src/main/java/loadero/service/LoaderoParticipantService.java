package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

public class LoaderoParticipantService extends AbstractLoaderoService<LoaderoParticipant> {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder         = super.getUrlBuilder();

    public LoaderoParticipantService(LoaderoCrudController crudController,
                                     LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoParticipant getById(String... id) {
        String testId = id[0];
        String groupId = id[1];
        String participantId = id[2];
        LoaderoClientUtils.checkArgumentsForNull(testId, groupId, participantId);

        String particUrl = buildUrl(testId, groupId, participantId);
        return (LoaderoParticipant) crudController.get(
                particUrl, LoaderoType.LOADERO_PARTICIPANT
        );
    }

    @Override
    public LoaderoParticipant updateById(LoaderoParticipant newModel, String... id) {
        String testId = id[0];
        String groupId = id[1];
        String participantId = id[2];

        LoaderoClientUtils.checkArgumentsForNull(newModel, testId, groupId, participantId);

        String participantUrl = buildUrl(testId, groupId, participantId);
        LoaderoParticipant currentParticInfo = getById(testId, groupId, participantId);
        LoaderoModel updatedParticipant = LoaderoClientUtils
                .copyUncommonFields(
                        currentParticInfo,
                        newModel,
                        LoaderoType.LOADERO_PARTICIPANT);

        return (LoaderoParticipant) crudController
                .update(participantUrl, LoaderoType.LOADERO_PARTICIPANT, updatedParticipant);
    }

    @Override
    protected String buildUrl(String... id) {
        return String.format("%s/", urlBuilder.buildParticipantURL(id[0], id[1], id[2]));
    }
}
