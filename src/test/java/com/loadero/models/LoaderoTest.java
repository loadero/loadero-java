package com.loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.model.Group;
import com.loadero.model.GroupParams;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LoaderoTest extends AbstractTestLoadero {
  protected static String groupUrl;

  @Test
  public void testCopyUncommonField() {
    GroupParams currentParams =
        GroupParams.builder().withId(40).withCount(1).withName("current params").build();

    GroupParams updateParams =
        GroupParams.builder().withId(30).withCount(1).withName("Updated params").build();
//    GroupParams newParams = currentParams.copyUncommonFields(updateParams);
//    Group updatedGroup = Group.update(newParams);
  }

  @Test
  public void testApiResource() throws IOException {
    groupUrl = String.format("%s/tests/%s/groups", Loadero.getProjectUrl(), TEST_ID);
    String url = String.format("%s/%s/", groupUrl, GROUP_ID);

    StubMapping stub =
        wmRule.stubFor(
            get(urlMatching(".*/groups/[0-9]+/"))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-groups-48797-m03sm.json")));

    Group group = ApiResource.request(RequestMethod.GET, url, null, Group.class);
    Assertions.assertNotNull(group);
  }
}
