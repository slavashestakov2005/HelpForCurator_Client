/**
 * Класс автоматического запуска фонового процесса-обновлятеля
 * **/

package com.example.helpforcurator.help;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoRun extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // получили boot_completed - запустили FoneService
            context.startService(new Intent(context, FoneService.class));
        }
    }
}
