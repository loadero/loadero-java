package loadero.scripts.testui;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import testUI.elements.UIElement;

import static testUI.UIOpen.open;
import static testUI.Utils.AppiumHelps.sleep;
import static testUI.Utils.By.byCssSelector;
import static testUI.Utils.By.byId;
import static testUI.elements.TestUI.E;

@Disabled
public class TestOneOnOneCall {

    @Test
    public void testScript() {
        int particId = 0;
        int callDuration = 0;
        String appUrl = "";
        int elementTimeout = 0;
        String identity = String.format("Participant%d", particId);

        if (particId % 2 == 0) {
            String caller = String.format("%s%s", appUrl, identity);
            String toCall = String.format("Participant%d", particId+1);
            UIElement element = E(byId("button-hangup"));

            open(caller)
                    .setElement(byCssSelector("body")).waitFor(elementTimeout).untilIsVisible()
                    .setElement(byId("call-controls")).waitFor(elementTimeout).untilIsVisible()
                    .setElement(byId("phone-number")).waitFor(elementTimeout).untilIsVisible().then().sendKeys(toCall)
                    .setElement(byId("button-call")).waitFor(elementTimeout).untilIsVisible().then().click()
                    .setElement(byId("log")).waitFor(elementTimeout).untilHasText("Successfully established call!");
            sleep(callDuration * 1000);
            element.waitFor(callDuration * 1000).untilIsVisible().then().click();

        } else {
            String callee = String.format("%s%s", appUrl, identity);
            open(callee)
                    .setElement(byCssSelector("body")).waitFor(elementTimeout).untilIsVisible()
                    .setElement(byId("call-controls")).waitFor(elementTimeout).untilIsVisible()
                    .setElement(byId("log")).waitFor(elementTimeout).untilHasText("Ready")
                    .setElement(byId("log")).waitFor(callDuration*1000).untilHasText("Call ended");
        }
    }
}
