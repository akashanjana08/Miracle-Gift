package wheretospeach.ndm.com.wheretospeach.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Ndm-PC on 9/23/2016.
 */
public class AndroidPermissions
{
    public static void permissions(Context mContext)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (!android.provider.Settings.System.canWrite(mContext))
            {
                ((Activity)mContext).requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE
                        , android.Manifest.permission.CAMERA}, 4);

            }

        }
    }

    public static  boolean checkPermission(Context mContext)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);


        if(permissionCheck==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
