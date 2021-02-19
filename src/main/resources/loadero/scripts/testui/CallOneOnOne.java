public void test() {
        int particId = globalConfig.getParticipant().getId();
        String identity = String.format("Participant%d", particId);
        int callDuration = 60; // in seconds
        String appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
        int elTimeout = 10;

        if (particId % 2 == 0) {
            String caller = String.format("%s%s", appUrl, identity);
            String toCall = String.format("Participant%d", particId+1);

            open(caller)
                .setElement(byCssSelector("body")).waitFor(elTimeout).untilIsVisible().shouldBe().visible()
                .setElement(byId("phone-number")).waitFor(elTimeout).untilIsVisible().shouldBe().visible()
                .setElement(byId("call-controls")).waitFor(elTimeout).untilIsVisible().shouldBe().visible()
                .setElement(byId("phone-number")).waitFor(elTimeout).untilIsVisible()
                .sendKeys(toCall)
                .setElement(byId("button-call")).waitFor(elTimeout).untilIsVisible().click()
                .setElement(byId("button-hangup")).waitFor(elTimeout).untilIsVisible()
                .setElement(byId("log")).shouldHave().containNoCaseSensitiveText("Successfully established call!")
                .setElement(byId("button-hangup")).waitFor(callDuration*1000).click();
        } else {
            String callee = String.format("%s%s", appUrl, toCall);
            open(callee)
                .setElement(byCssSelector("body")).waitFor(elTimeout).untilIsVisible()
                .setElement(byId("phone-number")).waitFor(elTimeout).untilIsVisible()
                .click("phone-number")
                .setElement(byId("log")).waitFor(elTimeout).untilIsVisible().shouldHave().containNoCaseSensitiveText("Ready")
                .setElement(byId("button-hangup")).waitFor(elTimeout).untilIsVisible()
                .setElement(byId("log")).waitFor(callDuration*1000).shouldHave().containNoCaseSensitiveText("Call ended");
        }
}

