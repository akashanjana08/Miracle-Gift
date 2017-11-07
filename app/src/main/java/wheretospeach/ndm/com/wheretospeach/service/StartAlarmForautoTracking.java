package wheretospeach.ndm.com.wheretospeach.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import wheretospeach.ndm.com.wheretospeach.recievers.AlarmReceiver;

/**
 * Created by Ndm-PC on 1/11/2016.
 */
public class StartAlarmForautoTracking
{
    private PendingIntent pendingIntent;
    public  void startTracking(Context ctx)
    {

        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ctx, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager)ctx. getSystemService(Context.ALARM_SERVICE);

        /* Repeating on every 10 minutes interval */
        //int interval = 1000 *60 * 9;
         int interval = 1000 * 10;
        // int interval = 1000*60 * 5;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 59);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Toast.makeText(ctx, "Alarm Set", Toast.LENGTH_SHORT).show();

    }



    public void stopTracking(Context ctx)
    {

        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ctx, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager)ctx. getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
       // Toast.makeText(ctx, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10(Context ctx) {
        AlarmManager manager = (AlarmManager)ctx. getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 59);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }

}
