package com.example.pocketcloset;

import android.app.Application;

import com.parse.Parse;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3O0Ck3UNIFJHjaz7t40gXT5LQb4Bt7DTaskTs2WQ")
                .clientKey("DY3DQ6eiXTOkMun07229C90VZbxeChDh3jUWS09k")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
