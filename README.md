# Firebase App Init

This plugin sets up an additional firebase app for a Cordova app.

## Setup

1. Clone the plugin (not yet released on NPM)

2. Install this plugin using Cordova CLI:

    npx cordova plugin add ../path/to/plugin

3. Configure the plugin by adding the firebase app info to the app's config.xml:

<preference name="com.airship.firebase.project_id" value="string:<PROJECT_NAME>" />
<preference name="com.airship.firebase.application_id" value="string:<APPLICATION_ID>" />
<preference name="com.airship.firebase.api_key" value="string:<API_KEY>" />
<preference name="com.airship.firebase.sender_id" value="string:<SENDER_ID>" />
<preference name="com.airship.firebase.app_name" value="string:<APP_NAME>" />

