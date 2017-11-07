package wheretospeach.ndm.com.wheretospeach;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMReciever extends WakefulBroadcastReceiver {
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		ComponentName comp=new ComponentName(context.getPackageName(),GCMMessageHandler.class.getName());
		
		
		startWakefulService(context, (intent.setComponent(comp)));
		
		setResultCode(Activity.RESULT_OK);
		
		
		
	}
	
	

}
