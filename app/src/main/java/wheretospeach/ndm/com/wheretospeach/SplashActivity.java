package wheretospeach.ndm.com.wheretospeach;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.facebook.internal.Utility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONObject;

import wheretospeach.ndm.com.wheretospeach.database.SpeachDatabase;
import wheretospeach.ndm.com.wheretospeach.general.CalanderFormat;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.ExportDatabaseData;
import wheretospeach.ndm.com.wheretospeach.general.SharedPrefence;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.permission.AndroidPermissions;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity implements  GoogleApiClient.ConnectionCallbacks
{
    GoogleCloudMessaging gcm;
    SharedPreferences shareprefraence;
    SharedPreferences.Editor edit;
    Intent myintent =  null;
    GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_FINE_LOCATION=0;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    int closeappstatus=0;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        ExportDatabaseData.exportDB("Speech_Database", this);
        new SpeachDatabase(this);

        initialize();

        settingsrequest();








        t=new Thread()
        {


            @Override
            public void run() {

                try {

                    Thread.sleep(1000);
                }
                catch (Exception e)
                {

                }
                finally {


                    String refstatus=shareprefraence.getString("USER_ID", "0");
                    if(refstatus.equalsIgnoreCase("0"))
                    {
                        Intent i=new Intent(getApplicationContext(),RegisterUserActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {

                        Intent ih=new Intent(getApplicationContext(),MapsActivity.class);
                        startActivity(ih);
                        finish();
                    }




                }
            }
        };

    }

    void needGCM()
    {

        String appID=shareprefraence.getString("APPLICATION_ID", "N");
        if(appID.equals("N") || appID.length()<20)
        {

            if(ConnectionDetector.isConnectingToInternet(this))
            {
                getRegId();
            }
            else
            {

                Toast.makeText(this,"Internet Required For first Login",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            t.start();

        }


    }

    void getRegId()
    {
        new AsyncTask<String, Void, String>(){

            ProgressDialog pd;
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
//    			pd=ProgressDialog.show(getApplicationContext(), "Wait", "Please");
            }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                String regig=null;
                try {

                    if(gcm==null)
                    {

                        gcm= GoogleCloudMessaging.getInstance(getApplicationContext());

                    }
                    regig= gcm.register("374711969100");

                } catch (Exception e) {
                    // TODO: handle exception
                    return e.getMessage();
                }
                return regig;
            }

            @Override
            protected void onPostExecute(String result)
            {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                edit.putString("APPLICATION_ID", result);


                edit.commit();



                String deviceId = Settings.Secure.getString(SplashActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                JSONObject jsonObj = new JSONObject();
                JSONArray jar = new JSONArray();
                try {
                    jar.put(jsonObj.put("appid", result).put("deviceId", deviceId).put("dateTime", CalanderFormat.getDateTimeCalender()));

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                if (ConnectionDetector.isConnectingToInternet(SplashActivity.this))
                {

                    myintent=new Intent(SplashActivity.this,RegisterUserActivity.class);
                    startActivity(myintent);
                    finish();


                   // new HttpAsyncResult(SplashActivity.this, WebUrl.DEVICE_REGISTER_URL, jar.toString(), "DEVICE_REGISTER", SplashActivity.this).execute();
                } else {
                    Toast.makeText(SplashActivity.this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                }

            }

        }.execute();


    }


    private void initialize()
    {

        shareprefraence = SharedPrefence.getSharedPrefence(this);
        edit=shareprefraence.edit();

    }




    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void settingsrequest()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //Toast.makeText(MainActivity.this,"SUCCESS",Toast.LENGTH_LONG).show();
                        needGCM();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.

                        if(closeappstatus==0)
                        {

                            try {
                                // Toast.makeText(MainActivity.this,"RESOLUTION_REQUIRED:",Toast.LENGTH_LONG).show();
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().

                                status.startResolutionForResult(SplashActivity.this, REQUEST_CHECK_SETTINGS);

                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                        }
                        else
                        {
                            finish();

                        }

                        closeappstatus++;


                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        // Toast.makeText(MainActivity.this,"SETTINGS_CHANGE_UNAVAILABLE",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // startLocationUpdates();
                           needGCM();
                        //Toast.makeText(this,"RESULT OK",Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                              settingsrequest();//keep asking if imp or do whatever
                        break;
                }
                break;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                }
                else{
                    // no granted
                }
                return;
            }

        }

    }

}
