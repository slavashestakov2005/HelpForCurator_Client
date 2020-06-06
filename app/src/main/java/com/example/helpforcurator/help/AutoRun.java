package com.example.helpforcurator.help;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoRun extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("chat", "+ AutoRun - начал");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            // получили boot_completed - запустили FoneService
            context.startService(new Intent(context, FoneService.class));
            Log.i("chat", "+ AutoRun - отработал");
        }
    }
}
