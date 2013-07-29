package com.example.smsmonitor;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ichuraev on 26.07.13.
 */
public class SmsStartService extends Service {
    String RECEIVER = "SMS_RECEIVER";
    SmsReceiver smsReceiver = new SmsReceiver();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerReceiver(smsReceiver, new IntentFilter(RECEIVER));
        startService();
        Log.v(this.getClass().getName(), "onCreate(..)");
    }

    private void startService() {
        // TODO: start receiver
        for(;;);
    }
}
