package wheretospeach.ndm.com.wheretospeach.gps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import wheretospeach.ndm.com.wheretospeach.general.CalanderFormat;
import wheretospeach.ndm.com.wheretospeach.general.SharedPrefence;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;

/**
 * Created by Ndm-PC on 9/2/2016.
 */
public class SendAddress {

    SharedPreferences shareprefraence;
    SharedPreferences.Editor edit;

    public void callservice(Context mContext, String textmsg) {

        shareprefraence = SharedPrefence.getSharedPrefence(mContext);
        edit = shareprefraence.edit();
        try {
            String recieverId = shareprefraence.getString("RECIEVER_ID", "No ID");
            textmsg = URLEncoder.encode(textmsg, "utf-8");

            String senderId=shareprefraence.getString("USER_ID", "");
            String senderName=shareprefraence.getString("USER_Name", "");


            String dateTime =URLEncoder.encode(CalanderFormat.getDateTimeCalender(), "utf-8");



            new SendAddressServer(mContext, WebUrl.SEND_LOCATION_URL + "?textMessage=" + textmsg +
                    "&userId=" + recieverId+
                    "&dateTime=" + dateTime+
                    "&senderId=" + senderId+
                    "&senderName=" + senderName).execute();


            //new SendAddressServer(mContext, WebUrl.SEND_LOCATION_URL + "?textMessage=" + textmsg + "&userId=" + recieverId).execute();
        } catch (Exception e) {

        }

    }

    public class SendAddressServer extends AsyncTask<String,Void,String> {

        Context mContext;
        String URLString=null;

        public SendAddressServer(Context mContext,String URLString)
        {
            this.mContext=mContext;

            this.URLString= URLString;

        }




        @Override
        protected String doInBackground(String... params) {


            //String response = "";
            StringBuffer response = new StringBuffer();

            try
            {
                URL url = new URL(URLString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");






                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null)
                    {

                        //response+=line;
                        response.append(line);

                    }
                }
                else
                {
                    response.toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response.toString();


        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);



        }
    }










}


