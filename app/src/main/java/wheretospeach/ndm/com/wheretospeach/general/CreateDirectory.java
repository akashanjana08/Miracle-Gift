package wheretospeach.ndm.com.wheretospeach.general;

import android.os.Environment;

import java.io.File;

/**
 * Created by Ndm-PC on 8/18/2016.
 */
public class CreateDirectory {


    static public void makeDirectory()
    {

        File direct = new File(Environment.getExternalStorageDirectory() + "/gabbar/gabbarvideo");
        File direct1 = new File(Environment.getExternalStorageDirectory() + "/gabbar/images");


        if (!direct1.exists())
        {
            File wallpaperDirectory = new File("/sdcard/gabbar/images/");
            wallpaperDirectory.mkdirs();
        }

        if (!direct.exists())
        {
            File wallpaperDirectory = new File("/sdcard/gabbar/gabbarvideo/");
            wallpaperDirectory.mkdirs();
        }
    }


}
