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
import com.loadero.model.Precondition;
import com.loadero.model.PreconditionParams;
import com.loadero.types.AssertOperator;
import com.loadero.types.Property;
import java.io.IOException;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestPrecondition extends AbstractTestLoadero {
    private static final String precondFile = "body-precondition.json";
    private static final int PRECONDITION_ID = 692;

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void retrievePrecondition() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/preconditions/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(precondFile))
        );

        Precondition read = Precondition.read(TEST_ID, ASSERT_ID, PRECONDITION_ID);
        Assertions.assertNotNull(read);
        Assertions.assertNotNull(read.getOperator());
        Assertions.assertNotNull(read.getProperty());
        Assertions.assertNotNull(read.getExpected());
    }

    @Test
    public void PreconditionNotFound() {
        wmRule.stubFor(get(urlMatching(".*/preconditions/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(404))
        );
        Assertions.assertThrows(ApiException.class, () -> {
            Precondition notFound = Precondition.read(TEST_ID, GROUP_ID, 1);
        });
    }

    @Test
    public void createAndDeletePrecondition() throws IOException {
        wmRule.stubFor(post(urlMatching(".*/preconditions/"))
            .willReturn(aResponse()
                .withStatus(201)
                .withBodyFile(precondFile))
        );

        wmRule.stubFor(delete(urlMatching(".*/preconditions/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(204))
        );

        PreconditionParams params = PreconditionParams.builder()
            .withTestId(TEST_ID)
            .withAssertId(ASSERT_ID)
            .withProperty(Property.MEDIA_TYPE)
            .withOperator(AssertOperator.GREATER_OR_EQUAL)
            .withExpected("4232")
            .build();

        Precondition create = Precondition.create(params);
        Assertions.assertNotNull(create);
        Assertions.assertNotNull(create.getExpected());
        Assertions.assertNotNull(create.getProperty());
        Assertions.assertNotNull(create.getOperator());

        Precondition.delete(TEST_ID, ASSERT_ID, create.getId());
    }

    @Test
    public void missingTestIdCreatePrecondition() throws IOException {
        wmRule.stubFor(post(urlMatching(".*/preconditions/"))
            .willReturn(aResponse()
                .withStatus(404))
        );

        PreconditionParams params = PreconditionParams.builder()
            .withAssertId(ASSERT_ID)
            .withProperty(Property.MEDIA_TYPE)
            .withOperator(AssertOperator.GREATER_OR_EQUAL)
            .withExpected("4232")
            .build();

        Assertions.assertThrows(ApiException.class, () -> {
            Precondition notFound = Precondition.create(params);
        });
    }

    @Test
    public void updatePrecondition() throws IOException {
        String url = ".*/preconditions/[0-9]*/";
        wmRule.stubFor(get(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(precondFile))
        );

        PreconditionParams mockParams = PreconditionParams.builder()
            .withTestId(TEST_ID)
            .withAssertId(ASSERT_ID)
            .withId(PRECONDITION_ID)
            .withUpdated("date")
            .withCreated("date")
            .withOperator(AssertOperator.EQUAL)
            .withProperty(Property.MEDIA_TYPE)
            .withExpected("111")
            .build();

        Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

        wmRule.stubFor(put(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200).withBody(gson.toJson(mockParams)))
        );

        PreconditionParams updateParams = PreconditionParams.builder()
            .withId(PRECONDITION_ID)
            .withTestId(TEST_ID)
            .withAssertId(ASSERT_ID)
            .withExpected("111")
            .withOperator(AssertOperator.EQUAL)
            .build();
        Precondition update = Precondition.update(updateParams);
        Assertions.assertNotNull(update);
        Assertions.assertEquals(AssertOperator.EQUAL, update.getOperator());
        Assertions.assertEquals("111", update.getExpected());
    }

    @Test
    public void negativeUpdate() throws IOException {
        String url = ".*/preconditions/[0-9]*/";
        wmRule.stubFor(get(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(precondFile))
        );

        wmRule.stubFor(put(urlMatching(url))
            .willReturn(aResponse()
                .withStatus(422))
        );

        Assertions.assertThrows(ApiException.class, () -> {
            PreconditionParams mockParams = PreconditionParams.builder()
                .build();
            Precondition update = Precondition.update(mockParams);
        });
    }

    @Test
    public void getProperty() {
        Property mediaType = Property.valueOf("media_type".toUpperCase(Locale.ROOT));
        Property cu = Property.valueOf("compute_unit".toUpperCase(Locale.ROOT));
        Assertions.assertEquals(Property.MEDIA_TYPE, mediaType);
        Assertions.assertEquals(Property.COMPUTE_UNIT, cu);
        Assertions.assertEquals("media_type", Property.MEDIA_TYPE.toString().toLowerCase());
        Assertions.assertEquals("compute_unit", Property.COMPUTE_UNIT.toString().toLowerCase());
    }
}
