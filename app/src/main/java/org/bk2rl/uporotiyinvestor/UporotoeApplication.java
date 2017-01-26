package org.bk2rl.uporotiyinvestor;

import android.app.Application;

//import com.facebook.FacebookActivity;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;

import org.bk2rl.uporotiyinvestor.model.RandomItems;

public class UporotoeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RandomItems.initialize(this);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
    }
}
