package org.bts_netmind.dataadaptermanager;

/**
 * Created by DELL on 4/8/2017.
 */


import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class Notifications extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(MainActivity.NOTIFICATION_ID);
    }
}
