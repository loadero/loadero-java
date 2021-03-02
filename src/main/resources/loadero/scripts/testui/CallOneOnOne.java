public class CallOneOnOne {
    public void test() {
        int particId = globalConfig.getParticipant().getId();
        String identity = String.format("Participant%d", particId);
        int callDuration = 60; // in seconds
        String appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
        int elTimeout = 10;
        
        if (particId % 2 == 0) {
            String caller = String.format("%s%s", appUrl, identity);
            String toCall = String.format("Participant%d", particId+1);
            UIElement element = E(byId("button-hangup"));
            
            open(caller)
                    .setElement(byCssSelector("body")).waitFor(elTimeout).untilIsVisible()
                    .setElement(byId("call-controls")).waitFor(elTimeout).untilIsVisible()
                    .setElement(byId("phone-number")).waitFor(elTimeout).untilIsVisible().then().sendKeys(toCall)
                    .setElement(byId("button-call")).waitFor(elTimeout).untilIsVisible().then().click()
                    .setElement(byId("log")).waitFor(elTimeout).untilHasText("Successfully established call!");
            sleep(callDuration * 1000);
            element.waitFor(callDuration * 1000).untilIsVisible().then().click();
            
        } else {
            String callee = String.format("%s%s", appUrl, identity);
            open(callee)
                    .setElement(byCssSelector("body")).waitFor(elTimeout).untilIsVisible()
                    .setElement(byId("call-controls")).waitFor(elTimeout).untilIsVisible()
                    .setElement(byId("log")).waitFor(elTimeout).untilHasText("Ready")
                    .setElement(byId("log")).waitFor(callDuration*1000).untilHasText("Call ended");
        }
    }
}


