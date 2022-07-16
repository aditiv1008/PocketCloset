package com.example.pocketcloset;

import android.app.Application;

import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.Outfit;
import com.example.pocketcloset.models.User;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Clothing.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Outfit.class);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3O0Ck3UNIFJHjaz7t40gXT5LQb4Bt7DTaskTs2WQ")
                .clientKey("DY3DQ6eiXTOkMun07229C90VZbxeChDh3jUWS09k")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
