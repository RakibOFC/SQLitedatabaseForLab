package com.rakibofc.sqlitedatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Hurry Up!!!", Toast.LENGTH_SHORT).show();
    }
}