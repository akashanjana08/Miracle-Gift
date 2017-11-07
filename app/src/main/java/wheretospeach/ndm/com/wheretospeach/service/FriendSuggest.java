package wheretospeach.ndm.com.wheretospeach.service;

import android.content.Context;
import android.widget.Toast;

import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;

/**
 * Created by Ndm-PC on 9/24/2016.
 */
public class FriendSuggest implements AsyncResult
{
  public  void getSuggestions(Context mContext,String userId)
  {

      if (ConnectionDetector.isConnectingToInternet(mContext))
      {

          new HttpAsyncResult(null, WebUrl.USER_FRIEND_SUGGEST+"?userId="+userId, null, "FRIEND_SUGGEST", this).execute();
      }
      else
      {
          Toast.makeText(mContext, "Please Connect To Internet", Toast.LENGTH_LONG).show();
      }
  }


    @Override
    public void getResponse(String key, String data)
    {

        String dataResponse=data;
    }
}