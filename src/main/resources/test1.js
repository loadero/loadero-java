function(client) {
    // Example of locating elements using xPath selectors
    client
        .useXpath()
        // Navigate to Google website
        .url('https://www.google.com')
        // Wait up to 10 seconds until '//body' element is visible
        .waitForElementVisible('//body', 10* 1000)
        // Type "charizard evolution chart" in the search bar
        .setValue('//input[@type="text"]', 'charizard evolution chart')
         // Trigger search by sending 'Enter' key event in the search bar
        .setValue('//input[@type="text"]', client.Keys.ENTER);
}