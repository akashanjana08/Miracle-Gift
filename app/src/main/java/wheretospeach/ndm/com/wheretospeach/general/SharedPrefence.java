package wheretospeach.ndm.com.wheretospeach.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefence
{


	public static SharedPreferences getSharedPrefence(Context ctx)
	{
		
		SharedPreferences sharedprefence=ctx.getSharedPreferences("GABBAR_APP", Context.MODE_PRIVATE);
		return sharedprefence;
	}
	
	public static Editor getSharedPrefenceEditor(Context ctx)
	{
		
		   Editor et=getSharedPrefence(ctx).edit();
		   return et;
	}
	
}
