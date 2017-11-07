package wheretospeach.ndm.com.wheretospeach.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import wheretospeach.ndm.com.wheretospeach.gps.AutoTrackLocation;


public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
       // Toast.makeText(context, "Alarm Receiver", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, AutoTrackLocation.class));
    }



}