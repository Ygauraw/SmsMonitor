package com.example.smsmonitor;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.util.Log;
/**
 * Created by ichuraev on 25.07.13.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent)
    {
        // get the SMS message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            // retrieve the SMS message received
            Object[] pdus = (Object[])bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            if (msgs.length > 0)
            {
                msgs [0] = SmsMessage.createFromPdu((byte[])pdus[0]);
                str = "SMS from " + FindContact(context,msgs[0].getOriginatingAddress());
                str += ":\n";
            }
            for (int i = 0; i < msgs.length; i++)
            {
                msgs [i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += msgs[i].getMessageBody().toString();
            }
            str += "\n";
            Log.i("Message: ", str);
            // Display the new SMS
            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
        }
    }

    private String FindContact (Context context, String number)
    {
        String contactName = "";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));
        ContentResolver cr = context.getContentResolver();
        // Didn't work because I forgot to give permission
        Cursor c = cr.query(uri, new String[] {BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToNext();
                contactName = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        if (contactName == "")
            contactName = number;
        return contactName;
    }
}
