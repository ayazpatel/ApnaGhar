package com.example.apnaghar;

import android.content.Context;

import com.onesignal.OneSignal;

public class Constants {
    public String APP_SERVER_SIGNAL_ID = "154f9590-dafd-4bd5-858d-23ec30506fad";

    public void GET_SERVER_SIGNAL(Context context,String id) {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE,OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(context);
        OneSignal.setAppId(id);
        OneSignal.promptForPushNotifications(true);
//        OneSignal.promptLocation();
    }
}