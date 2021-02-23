function test(client) {
    var globalConfig = client.globals;
    var particId = globalConfig.participant.id;
    var identity = "Participant" + particId;
    var callDuration = 60; // in seconds
    var appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
    var elementTimeout = 10*1000;

    if (particId % 2 == 0) {
        var caller = appUrl + identity;
        var toCall = "Participant" + (particId + 1);

        client
            .url(caller)
            .waitForElementVisible('body', elementTimeout)
            .waitForElementVisible('input[id=phone-number]', elementTimeout)
            .waitForElementVisible('div[id=call-controls]', elementTimeout)
            .pause(elementTimeout)
            .setValue('input[id=phone-number]', toCall)
            .waitForElementVisible('button[id=button-call]', elementTimeout)
            .click('button[id=button-call]')
            .waitForElementVisible('button[id=button-hangup]', elementTimeout)
            .assert.containsText('div[id=log]', 'Successfully established call!')
            .pause(callDuration*1000)
            .click('button[id=button-hangup]');
    } else {
        var callee = appUrl + identity;
        client
            .url(callee)
            .waitForElementVisible('body', elementTimeout)
            .waitForElementVisible('input[id=phone-number]', elementTimeout)
            .click('input[id=phone-number]')
            .waitForElementVisible('div[id=call-controls]', elementTimeout)
            .waitForElementVisible('div[id=log]', elementTimeout)
            .assert.containsText('div[id=log]', 'Ready')
            .waitForElementVisible('button[id=button-hangup]', elementTimeout)
            .pause(callDuration*1000)
            .waitForElementVisible('button[id=button-call]', elementTimeout)
            .assert.containsText('div[id=log]', 'Call ended');
    }
}