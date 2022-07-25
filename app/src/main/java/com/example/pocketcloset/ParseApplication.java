package com.example.pocketcloset;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.Outfit;
import com.example.pocketcloset.models.User;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Outfit.class);
        ParseObject.registerSubclass(Clothing.class);
        ParseObject.registerSubclass(User.class);



        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3O0Ck3UNIFJHjaz7t40gXT5LQb4Bt7DTaskTs2WQ")
                .clientKey("DY3DQ6eiXTOkMun07229C90VZbxeChDh3jUWS09k")
                .server("https://parseapi.back4app.com")
                .build()
        );

        ArrayList<String> channels = new ArrayList<>();
        channels.add("News");

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "175249669352");
        installation.put("channels", channels);
        installation.saveInBackground();


        final HashMap<String, String> params = new HashMap<>();
// Calling the cloud code function





    }
    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParseApplication.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


}
