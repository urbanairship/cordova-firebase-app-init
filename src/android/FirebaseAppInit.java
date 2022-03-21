/* Copyright Urban Airship and Contributors */

package com.airship.cordova;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;
import androidx.startup.Initializer;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FirebaseAppInit implements Initializer<String> {

    private static final String CONFIG_PREFIX = "com.airship.firebase.";
    private static final String PROJECT_ID = CONFIG_PREFIX + "project_id";
    private static final String APPLICATION_ID = CONFIG_PREFIX + "application_id";
    private static final String API_KEY = CONFIG_PREFIX + "api_key";
    private static final String SENDER_ID = CONFIG_PREFIX + "sender_id";
    private static final String APP_NAME = CONFIG_PREFIX + "app_name";

    private static final String STRING_PREFIX = "string:";

    @NonNull
    @Override
    public String create(@NonNull Context context) {
        try {
            Map<String, String> config = parseConfigXml(context);

            // Required
            String projectId = ObjectsCompat.requireNonNull(config.get(PROJECT_ID));
            String applicationId = ObjectsCompat.requireNonNull(config.get(APPLICATION_ID));
            String apiKey = ObjectsCompat.requireNonNull(config.get(API_KEY));
            String appName = ObjectsCompat.requireNonNull(config.get(APP_NAME));

            String senderId = config.get(SENDER_ID);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId(projectId)
                    .setApplicationId(applicationId)
                    .setApiKey(apiKey)
                    .setGcmSenderId(senderId)
                    .build();

            FirebaseApp.initializeApp(context, options, appName);
            return appName;
        } catch (Exception e) {
            Log.e("FirebaseAppInit", "Failed to create Firebase app", e);
            return "";
        }
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }


    @NonNull
    public static Map<String, String> parseConfigXml(@NonNull Context context) {
        Map<String, String> config = new HashMap<String, String>();
        int id = context.getResources().getIdentifier("config", "xml", context.getPackageName());
        if (id == 0) {
            return config;
        }

        XmlResourceParser xml = context.getResources().getXml(id);

        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {

            if (eventType == XmlResourceParser.START_TAG) {
                if (xml.getName().equals("preference")) {

                    String name = xml.getAttributeValue(null, "name").toLowerCase(Locale.US);
                    if (name.startsWith(CONFIG_PREFIX)) {
                        String value = parseValue(xml.getAttributeValue(null, "value"));
                        if (value != null) {
                            config.put(name, value);
                        }
                    }
                }
            }

            try {
                eventType = xml.next();
            } catch (Exception e) {
                Log.e("FirebaseAppInit", "Failed to parse config", e);
            }
        }

        return config;
    }

    public static String parseValue(@Nullable String value) {
        if (value == null) {
            return null;
        }

        if (value.startsWith(STRING_PREFIX)) {
            return value.substring(STRING_PREFIX.length());
        }
        return value;
    }
}

