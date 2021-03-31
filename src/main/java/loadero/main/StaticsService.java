package loadero.main;

import loadero.model.MetricPaths;
import loadero.model.Statics;
import loadero.types.LoaderoModelType;

final class StaticsService extends AbstractService {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();
    
    public StaticsService(CrudController crudController,
                          CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }
    
    public MetricPaths getMetricPaths() {
        String metricPathsUrl = String.format("%smetric_path/", buildUrl());
        return (MetricPaths) crudController.get(metricPathsUrl, LoaderoModelType.LOADERO_METRIC_PATHS);
    }
    
    @Override
    public Statics getById(int... ids) {
        return (Statics) crudController.get(buildUrl(), LoaderoModelType.LOADERO_STATICS);
    }
    
    @Override
    public String buildUrl(int... ids) {
        return urlBuilder.getBaseUrl() + "/statics/";
    }
}
