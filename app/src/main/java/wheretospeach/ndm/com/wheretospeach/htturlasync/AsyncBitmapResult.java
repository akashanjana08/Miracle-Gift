package wheretospeach.ndm.com.wheretospeach.htturlasync;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import wheretospeach.ndm.com.wheretospeach.ImageBitmap.BitmapFromURL;

/**
 * Created by Ndm-PC on 5/17/2016.
 */
public interface AsyncBitmapResult
{
    public void getResponse(Object key, Bitmap data,LatLng latLng);


}
