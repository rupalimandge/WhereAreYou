package com.rupalimandge.whereareyou;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Rupali on 18/01/16.
 */
public class WhereAreYouApplication extends Application {

    String APPLICATION_ID = "gHloKdMHeEvVyXKURGG2c0wMNwjN6crxcycfYWvM";
    String CLIENT_KEY = "glkqXapKeiF4RjlFQgQT8LAEHXyguDwT8lr2NW0m";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
