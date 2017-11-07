package wheretospeach.ndm.com.wheretospeach;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Random;

import wheretospeach.ndm.com.wheretospeach.general.GeneralNotification;
import wheretospeach.ndm.com.wheretospeach.general.SharedPrefence;
import wheretospeach.ndm.com.wheretospeach.gps.TrackLocation;


public class GCMMessageHandler extends IntentService{

	String msg="",ms="",title="",notaction;
    private Handler handle;
	SharedPreferences shareprefraence;
	SharedPreferences.Editor edit;


	public GCMMessageHandler()
	{
		super("GCMMessageHandler");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		handle=new Handler();
		
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle extras=intent.getExtras();
		
		GoogleCloudMessaging gcm= GoogleCloudMessaging.getInstance(getApplicationContext());
		String msgType    =   gcm.getMessageType(intent);

		  
		   msg   = extras.getString("message");
		try {
			title = extras.getString("Title");
			notaction = extras.getString("Action");
			ms = extras.getString("msg");
		}
		catch (Exception e){}

		  showToast();
		  
		  GCMReciever.completeWakefulIntent(intent);

	}


	
	public void showToast()
	{

		handle.post(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub;
				//Toast.makeText(getAppplicationContext(), msg, Toast.LENGTH_LONG).show()
				Random rand = new Random();
				int  n = rand.nextInt(50) + 1;

				if(notaction.equalsIgnoreCase("2"))
                {

					ParseLocationData.getParseLocationData(msg,getApplicationContext(),n);
				}
				else
				 {
					 GeneralNotification.setNotificationMessage(getApplicationContext(), title, msg, n, FriendRequestListActivity.class);
					 startService(new Intent(getApplicationContext(), TrackLocation.class));

					 shareprefraence = SharedPrefence.getSharedPrefence(getApplicationContext());
					 edit=shareprefraence.edit();
					 edit.putString("RECIEVER_ID", ms);
					 edit.commit();
				 }
			}

		});
		
	}





	
	

}
