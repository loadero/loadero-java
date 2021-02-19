public class LoaderoScriptJava {

    @Test
    public void test() {
        int particId     = globalConfig.getParticipant().getId();
        String identity  = String.format("Participant%d", particId);
        String toCall    = "";
        String appUrl    = "";
        int callDuration = 60; // 1 minute

        updateNetwork(loaderoConstants.getNetwork().getDefault());

        if (particId % 2 == 0) {
            toCall = "Participant2";
            appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=" + identity;
            open(appUrl)
                    .setElement(byCssSelector("body")).waitFor(10).untilIsVisible()
                    .setElement(byId("phone-number")).waitFor(10).untilIsVisible().then().sendKeys(toCall)
                    .setElement(byId("button-call")).waitFor(10).untilIsVisible().click();
        } else {
            String recieveCall = "Participant2";
            appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=" + recieveCall;
            open(appUrl);
        }

        sleep(callDuration*1000);
    }
}