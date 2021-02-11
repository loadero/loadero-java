

public class LoaderoScriptJava {

    @Test
    public void testGoogleSearch() {
        // Example of locating elements using CSS selector
        open("https://www.google.com")
                // Wait 10 seconds until "body" is visible
                .setElement(byCssSelector("body")).waitFor(10).untilIsVisible()
                // Find search bar element
                .setElement(byCssSelector("input[type=text]"))
                // Type "loadero" in it
                .sendKeys("loadero")
                // Find search button and click it
                .setElement(byCssSelector("input[value~='Google']")).click();
    }
}
