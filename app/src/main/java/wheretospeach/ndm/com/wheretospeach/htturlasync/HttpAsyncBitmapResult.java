package wheretospeach.ndm.com.wheretospeach.htturlasync;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import wheretospeach.ndm.com.wheretospeach.ImageBitmap.BitmapFromURL;
import wheretospeach.ndm.com.wheretospeach.httpurlconnection.HttpUrlServerConnection;


/**
 * Created by Ndm-PC on 12/14/2015.
 */
public class HttpAsyncBitmapResult extends AsyncTask<String,Void,Bitmap>
{
    Context mContext;
    String URLString=null,JsonString=null;
    Object stringKey=null;
    ProgressDialog pdialog;
    AsyncBitmapResult asyncBitmapResult;
    LatLng latlng;

    public HttpAsyncBitmapResult(Context mContext, String URLString, String JsonString, Object stringKey, AsyncBitmapResult asyncBitmapResult,LatLng latlng)
    {
        this.mContext    = mContext;
        this.URLString   = URLString;
        this.JsonString  = JsonString;
        this.asyncBitmapResult = asyncBitmapResult;
        this.stringKey   = stringKey;
        this.latlng      = latlng;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pdialog= new ProgressDialog(mContext);
        pdialog.setMessage("Please Wait...");
       // pdialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        BitmapFromURL bitmapFromURL=new BitmapFromURL();
        Bitmap serverResult= bitmapFromURL.getBitmapFromURL(URLString);

        return serverResult;
    }


    @Override
    protected void onPostExecute(Bitmap result)
    {
        super.onPostExecute(result);
        //pdialog.dismiss();
        if(asyncBitmapResult!=null)
        {
            asyncBitmapResult.getResponse(stringKey,result,latlng);
        }

    }

}
