package wheretospeach.ndm.com.wheretospeach.gps;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class TrackLocation extends Service implements ConnectionCallbacks,OnConnectionFailedListener,LocationListener {


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private Location mLastLocation;

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
        super.onCreate();


        //shrpref= SharedPrefence.getSharedPrefence(getApplicationContext());
        
       // edit=shrpref.edit();
        
      if(checkPlayServices())
		{   
			 
			  buildGoogleApiClient();
			  
              createLocationRequest();
		}   
		
		
	}
	
	
	    private boolean checkPlayServices()
	    {
	    		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    		if (resultCode != ConnectionResult.SUCCESS)
	    		{
	    				if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
	    				{
							try {
								GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) getApplicationContext(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
							}
							catch (Exception e){}
	    				}
	    				else
	    				{
	    						Toast.makeText(getApplicationContext(),"This device is not supported.", Toast.LENGTH_LONG).show();

	    				}
	    				return false;
	    		}
	    		return true;
	    }
	    protected synchronized void buildGoogleApiClient()
	    {
	    		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
	    }
	    protected void createLocationRequest()
	    {
	    		mLocationRequest = new LocationRequest();
	    		mLocationRequest.setInterval(UPDATE_INTERVAL);
	    		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
	    		mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
	    		mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
	    }
	    @Override
	    @Deprecated
	    public void onStart(Intent intent, int startId)
	    {
	    	super.onStart(intent, startId);
	    	if (mGoogleApiClient != null)
	    	{
	    		mGoogleApiClient.connect();
	    	}
    	}
	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId)
	    {
	    	if (mGoogleApiClient.isConnected())
	    	{
	    		startLocationUpdates();
	    	}
	    	return super.onStartCommand(intent, flags, startId);
	    }
	    protected void startLocationUpdates()
	    {
	    	LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	    }
	    @Override
	    public void onConnected(Bundle arg0)
	    {
	    	startLocationUpdates();
	    }
	    @Override
	    public void onConnectionSuspended(int arg0)
	    {

		}
	    @Override
	    public void onConnectionFailed(ConnectionResult arg0)
	    {

	    }
	    @Override
	    public void onLocationChanged(Location location)
	    {
	    	mLastLocation=location;
	    	displayLocation();
	    }
	
	/**
     * Method to display the location on UI
     * */
    private void displayLocation()
	{

        int distance=0;
        String lastKnownLat=null,lastKnownLng=null;

        if (mLastLocation != null)
		{
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

			new LatLongtoAddress(getApplicationContext()).execute(latitude+"",longitude+"");

			Toast.makeText(getApplicationContext(), latitude+" "+longitude, Toast.LENGTH_LONG).show();
            
//            edit.putString("CURRENT_LATITUDE",String.valueOf(latitude));
//            edit.putString("CURRENT_LONGITUDE", String.valueOf(longitude));
//            edit.commit();

        } 
        else
		{
 
           Toast.makeText(getApplicationContext(), "Couldn't get the location.",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getApplicationContext(), "Display Call",Toast.LENGTH_SHORT).show();
        stopLocationUpdates();
        stopSelf();
    }
    
    /**
    * Stopping location updates
    */
      protected void stopLocationUpdates()
	    {
	    	LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	    }




}
