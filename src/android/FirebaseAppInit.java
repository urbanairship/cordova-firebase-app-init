/* Copyright Urban Airship and Contributors */

package com.airship.cordova;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

public class FirebaseAppInit implements Initializer<String> {

    private static final String APP_NAME = "secondary";

    @NonNull
    @Override
    public String create(@NonNull Context context) {
        // Manually configure Firebase Options. The following fields are REQUIRED:
        //   - Project ID
        //   - App ID
        //   - API Key
        //   - Sender ID
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("PROJECT_ID")
                .setApplicationId("APPLICATION_ID")
                .setApiKey("API_KEY")
                .setGcmSenderId("SENDER ID")
                .build();

        FirebaseApp.initializeApp(context, options, APP_NAME);
        return APP_NAME;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}