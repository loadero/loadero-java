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
import com.loadero.model.AssertParams;
import com.loadero.types.AssertOperator;
import com.loadero.types.MachineAsserts;
import com.loadero.types.WebRtcAsserts;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestAsserts extends AbstractTestLoadero {
    private static final String assertsPrecondFile = "body-asserts-precond.json";
    private static final String assertsNoPrecondFile = "body-asserts-no-precond.json";
    private static final String assertsFile = "body-all-asserts.json";
    private static final String url = ".*/asserts/[0-9]*/";

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

        AssertParams params = AssertParams.builder()
            .withTestId(TEST_ID)
            .withOperator(AssertOperator.EQUAL)
            .withPath(WebRtcAsserts.WEBRTC_AUDIO_JITTER_OUT_25TH)
            .withExpected("998")
            .build();

        Assert create = Assert.create(params);
        Assertions.assertNotNull(create);
        Assertions.assertNotNull(create.getOperator());
        Assertions.assertNotNull(create.getPath());

        wmRule.stubFor(delete(urlMatching(".*/asserts/" + create.getId() + "/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT))
        );
        Assert.delete(TEST_ID, create.getId());
    }

    @Test
    public void negativeCreate() {
        wmRule.stubFor(post(urlMatching(".*/asserts/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .withBodyFile(assertsNoPrecondFile))
        );

        AssertParams params = AssertParams.builder()
            .withTestId(TEST_ID)
            .withPath(WebRtcAsserts.WEBRTC_AUDIO_RTT_50TH)
            .build();

        Assertions.assertThrows(NullPointerException.class, () -> {
            Assert create = Assert.create(params);
        });
    }

    @Test
    public void updateAssert() throws IOException {
        AssertParams mockParams = AssertParams.builder()
            .withTestId(TEST_ID)
            .withId(ASSERT_ID)
            .withCreated("some date")
            .withUpdated("some date")
            .withPath(MachineAsserts.MACHINE_CPU_PERCENT_25TH)
            .withOperator(AssertOperator.GREATER_OR_EQUAL)
            .withExpected("expected")
            .withPreconditions(null)
            .build();

        Gson mockGson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
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
    public void negativeUpdate() {
        wmRule.stubFor(get(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(assertsPrecondFile))
        );

        wmRule.stubFor(put(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .withBody("")
            ));

        Assertions.assertThrows(ApiException.class, () -> {
            AssertParams params = AssertParams.builder()
                .withId(ASSERT_ID)
                .withTestId(TEST_ID)
                .build();
            Assert update = Assert.update(params);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            AssertParams params = AssertParams.builder()
                .withTestId(TEST_ID)
                .build();
            Assert update = Assert.update(params);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            AssertParams params = AssertParams.builder()
                .withId(ASSERT_ID)
                .build();
            Assert update = Assert.update(params);
        });
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
    public void negativeCopy() {
        wmRule.stubFor(post(urlMatching(url + "copy/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND)
            ));

        Assertions.assertThrows(ApiException.class, () -> {
            Assert copy = Assert.copy(TEST_ID, 1);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Assert copy = Assert.copy(1, ASSERT_ID);
        });
    }

    @Test
    public void testReadAll() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/asserts/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(assertsFile)
            )
        );

        List<Assert> asserts = Assert.readAll(TEST_ID);
        Assertions.assertNotNull(asserts);
    }

    @Test
    public void negativeReadAll() {
        wmRule.stubFor(get(urlMatching(".*/asserts/"))
            .willReturn(aResponse()
                .withStatus(404)
            )
        );

        Assertions.assertThrows(ApiException.class, () -> {
            List<Assert> asserts = Assert.readAll(112);
        });
    }
}
