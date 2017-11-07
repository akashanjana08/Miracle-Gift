package wheretospeach.ndm.com.wheretospeach.general;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import wheretospeach.ndm.com.wheretospeach.R;


/**
 * Created by Ndm-PC on 12/1/2015.
 */
public class GeneralNotification
{


 static public void  setNotificationMessage(Context mContext,String title,String message,int notiFyID,Class<?> cls )
 {

        NotificationManager notificationManager =(NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        int icon = R.drawable.audio_rec_icon;
        CharSequence notiText = "Location Alert";
        long meow = System.currentTimeMillis();
        notificationManager.cancel(notiFyID);


        CharSequence contentTitle = title;
        CharSequence contentText = message;
        Intent notificationIntent = new Intent(mContext, cls);

        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
             mContext);
//        Notification notification = mBuilder
//                .setContentTitle(contentTitle).setContentIntent(contentIntent)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setSmallIcon(icon).setAutoCancel(true).getNotification();

     Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
             .setAutoCancel(true)
             .setContentTitle(title)
             .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
             .setContentIntent(contentIntent)
             .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
             .setSmallIcon(icon)
             .setPriority(Notification.PRIORITY_HIGH)
             .setContentText(message).build();


     int SERVER_DATA_RECEIVED = notiFyID;
        notificationManager.notify(SERVER_DATA_RECEIVED, notification);
    }



}
