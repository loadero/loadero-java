package loadero.models;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import loadero.main.LoaderoClient;
import lombok.Getter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@Getter
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTestLoadero {
    protected static String token = System.getenv("LOADERO_API_TOKEN");
    protected static int PROJECT_ID           = 5040;
    protected static final int TEST_ID        = 6866;
    protected static final int PARTICIPANT_ID = 94633;
    protected static final int GROUP_ID       = 48797;
    protected static final int RUN_ID         = 33328;
    protected static final int RESULT_ID      = 930397;
    protected static String BASE_URL;
    protected static LoaderoClient loaderoClient;
    @Rule
    protected static WireMockRule wmRule = new WireMockRule(
            WireMockConfiguration
                    .wireMockConfig()
                    .dynamicPort()
    );

    protected static final Logger log = LogManager.getLogger(AbstractTestLoadero.class);
    protected CloseableHttpResponse response;
    protected final static Gson gson = new GsonBuilder()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    return fieldAttributes.getName().equals("id") ||
                            fieldAttributes.getName().equals("scriptFileId") ||
                            fieldAttributes.getName().equals("testId") ||
                            fieldAttributes.getName().equals("groupId");
                }
                
                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            })
            .create();
    
    @BeforeAll
    public static void setup() {
        BASE_URL = System.getenv("LOADERO_BASE_URL");
        wmRule.start();
        
        if (BASE_URL.contains("localhost")) {
            BASE_URL = BASE_URL + ":" + wmRule.port();
        }
        loaderoClient = new LoaderoClient(BASE_URL, token, PROJECT_ID);
    }
    
    @AfterAll
    public static void teardownWM() {
        wmRule.stop();
        BASE_URL = null;
    }
}
