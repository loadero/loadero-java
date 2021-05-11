package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Assert;
import com.loadero.model.AssertCollection;
import com.loadero.model.AssertParams;
import com.loadero.model.LoaderoCollection;
import com.loadero.types.AssertOperator;
import com.loadero.types.MachineAsserts;
import com.loadero.types.WebRtcAsserts;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestAsserts extends AbstractTestLoadero {
    private static final String assertsPrecondFile = "body-asserts-precond.json";
    private static final String assertsNoPrecondFile = "body-asserts-no-precond.json";

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void readAssert() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/asserts/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(assertsPrecondFile))
        );
        Assert read = Assert.read(TEST_ID, 47523);
        Assertions.assertNotNull(read);
        Assertions.assertNotNull(read.getPreconditions());
    }

    @Test
    public void negativeReadAssert() {
        wmRule.stubFor(get(urlMatching(".*/asserts/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(404))
        );
        Assertions.assertThrows(ApiException.class, () -> {
            Assert read = Assert.read(TEST_ID, 8);
        });
    }

    @Test
    public void createAndDeleteAssert() throws IOException {
        wmRule.stubFor(post(urlMatching(".*/asserts/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_CREATED)
                .withBodyFile(assertsNoPrecondFile))
        );

        wmRule.stubFor(delete(urlMatching(".*/asserts/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT))
        );

        AssertParams params = AssertParams.builder()
            .withTestId(TEST_ID)
            .withOperator(AssertOperator.EQUAL)
            .withPath(WebRtcAsserts.WEBRTC_AUDIO_JITTER_25TH)
            .withExpected("998")
            .build();

        Assert create = Assert.create(params);
        Assertions.assertNotNull(create);
        Assertions.assertNotNull(create.getOperator());
        Assertions.assertNotNull(create.getPath());

        Assert.delete(TEST_ID, create.getId());
    }

    @Test
    public void updateAssert() throws IOException {
        String url = ".*/asserts/[0-9]*/";
        AssertParams mockParams = AssertParams.builder()
            .withTestId(TEST_ID)
            .withId(ASSERT_ID)
            .withCreated("some date")
            .withUpdated("some date")
            .withPath(MachineAsserts.MACHINE_CPU_PERCENT_25TH)
            .withOperator(AssertOperator.GREATER_OR_EQUAL)
            .withExpected("expected")
            .build();

        Gson mockGson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
        System.out.println(mockGson.toJson(mockParams));

        wmRule.stubFor(get(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(assertsPrecondFile))
        );

        wmRule.stubFor(put(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(mockGson.toJson(mockParams))
            ));

        AssertParams params = AssertParams.builder()
            .withId(ASSERT_ID)
            .withTestId(TEST_ID)
            .withOperator(AssertOperator.GREATER_OR_EQUAL)
            .build();

        Assert update = Assert.update(params);
        Assertions.assertNotNull(update);
        Assertions.assertNotNull(update.getExpected());
        Assertions.assertEquals(AssertOperator.GREATER_OR_EQUAL, update.getOperator());
    }

    @Test
    public void testCopyAssert() throws IOException {
        String url = ".*/asserts/[0-9]*/";

        wmRule.stubFor(get(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(assertsPrecondFile))
        );
        wmRule.stubFor(post(urlMatching(url + "copy/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(assertsPrecondFile)
            ));
        Assert read = Assert.read(TEST_ID, ASSERT_ID);
        Assert copy = Assert.copy(TEST_ID, ASSERT_ID);

        Assertions.assertNotNull(copy);
        Assertions.assertEquals(read.getOperator(), copy.getOperator());
        Assertions.assertEquals(read.getPath(), copy.getPath());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testReadAll() throws IOException {
        List<Assert> assertList = Assert.readAll(TEST_ID);
        Assertions.assertNotNull(assertList);
    }
}
