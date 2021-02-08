import org.junit.Test;

import static testUI.UIOpen.open;
import static testUI.Utils.AppiumHelps.sleep;
import static testUI.Utils.By.byCssSelector;
import static testUI.Utils.By.byId;

public class ScriptForLoadero {

    @Test
    public void test() {
        // Example of locating elements using CSS selector and ID
        open("https://appr.tc/")
                // Wait 10 seconds until "body" element is visible
                .setElement(byCssSelector("body")).waitFor(10).untilIsVisible()
                // Find "join button" and click on it
                .setElement(byId("join-button")).click();

        // Wait for 10 seconds
        sleep(10 * 1000);
    }
}
