package loadero.models;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import loadero.LoaderoClient;
import loadero.utils.LoaderoHttpClient;
import loadero.utils.LoaderoUrlBuilder;
import lombok.Getter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

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
    protected static LoaderoUrlBuilder urlBuilder;
    @Rule
    protected static WireMockRule wmRule = new WireMockRule(
            WireMockConfiguration
                    .wireMockConfig()
                    .dynamicPort()
    );
    protected final LoaderoHttpClient httpClient = new LoaderoHttpClient(token);
    protected final Logger log = LogManager.getLogger(TestLoaderoPackageFunctionality.class);
    protected CloseableHttpResponse response;
    
    @BeforeAll
    public static void setup() {
        BASE_URL = System.getenv("LOADERO_BASE_URL");
        wmRule.start();
        
        if (BASE_URL.contains("localhost")) {
            BASE_URL = BASE_URL + ":" + wmRule.port();
        }
        urlBuilder = new LoaderoUrlBuilder(BASE_URL, PROJECT_ID);
        loaderoClient = new LoaderoClient(BASE_URL, token, PROJECT_ID);
    }
    
    @AfterAll
    public static void teardownWM() {
        wmRule.stop();
        BASE_URL = null;
    }
    
    /**
     * Makes GET request to specified url and saves response to
     * protected response variable.
     *
     * @param url - URL to make request to.
     */
    protected void makeGetRequest(String url) {
        try {
            HttpGet get = new HttpGet(url);
            response = httpClient.build().execute(get);
        } catch (IOException ex) {
            log.error("{}", ex.getMessage());
        }
    }
}
