package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Script;
import com.loadero.model.TestParams;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.TestMode;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoaderoTest extends AbstractTestLoadero {
    private static final String testFile = "body-projects-5040-tests-6866-uaor7.json";

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void testGetScriptContent() {
        TestParams params = TestParams
            .builder()
            .withScript(
                "src/main/resources/com/loadero/testui/CallOneOnOne.java",
                "test"
            )
            .build();

        TestParams params1 = TestParams
            .builder()
            .withScript("src/main/resources/loadero/scripts/nightwatch/test1.js")
            .build();

        System.out.println(params.getScript());
        System.out.println(params1.getScript());
        Assertions.assertNotNull(params.getScript());
        Assertions.assertNotNull(params1.getScript());
    }

    @Test
    public void testReadScript() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/files/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile("body-script-file.json"))
        );
        Script script = Script.read(57737);
        Assertions.assertNotNull(script);
        Assertions.assertNotNull(script.getContent());
    }

    @Test
    public void negativeReadScript() {
        wmRule.stubFor(get(urlMatching(".*/files/1/"))
            .willReturn(aResponse()
                .withStatus(404)
            ));

        Assertions.assertThrows(ApiException.class, () -> {
            Script script = Script.read(1);
        });
    }

    @Test
    public void testRetrieveTest() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/tests/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(testFile)
            ));

        com.loadero.model.Test test = com.loadero.model.Test.read(TEST_ID);
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getMode());
    }

    @Test
    public void negativeRetrieveTest() {
        wmRule.stubFor(get(urlMatching(".*/tests/1/"))
            .willReturn(aResponse()
                .withStatus(404)
            ));

        Assertions.assertThrows(ApiException.class, () -> {
            com.loadero.model.Test test = com.loadero.model.Test.read(1);
        });
    }

    @Test
    public void testCreateAndDeleteTest() throws IOException {
        wmRule.stubFor(post(urlMatching(".*/tests/"))
            .inScenario("create and delete test")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(201)
                .withBodyFile(testFile))
            .willSetStateTo("created")
        );

        wmRule.stubFor(delete(urlMatching(".*/tests/[0-9]*"))
            .inScenario("create and delete test")
            .whenScenarioStateIs("created")
            .willReturn(aResponse()
                .withStatus(204)));

        TestParams params = TestParams
            .builder()
            .withName("new test")
            .withMode(TestMode.LOAD)
            .withScript(
                "src/main/resources/com/loadero/testui/CallOneOnOne.java", "test")
            .withIncrementStrategy(IncrementStrategy.LINEAR_GROUP)
            .withStartInterval(Duration.ofSeconds(10))
            .withParticipantTimeout(Duration.ofSeconds(100))
            .build();
        com.loadero.model.Test create = com.loadero.model.Test.create(params);
        Assertions.assertNotNull(create);
        Assertions.assertNotNull(create.getMode());
        Assertions.assertNotNull(create.getIncrementStrategy());

        Assertions
            .assertThrows(ApiException.class, () -> com.loadero.model.Test.delete(create.getId()));
    }

    @Test
    public void testUpdateTest() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/tests/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(testFile))
        );

        wmRule.stubFor(get(urlMatching(".*/files/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile("body-script-file.json"))
        );

        com.loadero.model.Test current = com.loadero.model.Test.read(TEST_ID);

        wmRule.stubFor(get(urlMatching(".*/tests/[0-9]*/")).inScenario("update test")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(testFile))
            .willSetStateTo("read")
        );

        wmRule.stubFor(put(urlMatching(".*/tests/[0-9]*/")).inScenario("update test")
            .whenScenarioStateIs("read")
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(testFile))
        );

        TestParams updateParams = TestParams
            .builder()
            .withId(TEST_ID)
            .withParticipantTimeout(Duration.ofSeconds(200))
            .withIncrementStrategy(IncrementStrategy.RANDOM)
            .build();
        com.loadero.model.Test update = com.loadero.model.Test.update(updateParams);
        Assertions.assertEquals(current.getName(), update.getName());
        Assertions.assertEquals(current.getMode(), update.getMode());
        Assertions.assertEquals(IncrementStrategy.RANDOM, update.getIncrementStrategy());
    }

    @Test
    public void testEnumLookupHelper() {
        Assertions.assertThrows(ApiException.class, () ->{
            IncrementStrategy strategy = IncrementStrategy.getConstant("perf");
        });
    }
}