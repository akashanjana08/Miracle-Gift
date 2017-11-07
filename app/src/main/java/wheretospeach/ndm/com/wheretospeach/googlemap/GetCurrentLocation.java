package wheretospeach.ndm.com.wheretospeach.googlemap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;

public class GetCurrentLocation implements ConnectionCallbacks,OnConnectionFailedListener,LocationListener
{
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

	GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private static Location mLastLocation;
    ActionLocation actionLocation;
	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 1000*60*10; // 10 sec
	private static int FATEST_INTERVAL = 500; // 5 sec
	private static int DISPLACEMENT = 50; // 10 meters
	private LocationSource.OnLocationChangedListener mMapLocationListener = null;

	int REQUEST_CHECK_SETTINGS=5;

	Activity activity;
	public GetCurrentLocation(Activity activity, ActionLocation actionLocation)
	{
		this.activity = activity;
		this.actionLocation = actionLocation;
	}

	public void createGoogleApiClient()
	{
		if (checkPlayServices())
		{
			buildGoogleApiClient();
			createLocationRequest();
		}

	}

	public void connectGoogleApiClient()
	{
		if (mGoogleApiClient != null)
		{
			mGoogleApiClient.connect();
		}
	}

	public void updatesLocation()
	{
		checkPlayServices();
		if (mGoogleApiClient.isConnected())
		{
			startLocationUpdates();

		}
	}

	public void disconnectGoogleApiClient()throws Exception
	{
		stopLocationUpdates();
		if (mGoogleApiClient.isConnected())
		{
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle arg0)
	{
		startLocationUpdates();
	}
	@Override
	public void onConnectionSuspended(int arg0)
	{
		Toast.makeText(activity," ConnectionSuspended.",Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		Toast.makeText(activity," ConnectionFailed.",Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onLocationChanged(Location location)
	{

		mLastLocation=location;
		actionLocation.getLocation(location);

    }
	public Location getLocation()
	{
		return mLastLocation;
	}


	void googleApiClientServices()
	{
		if (mGoogleApiClient != null)
		{
			mGoogleApiClient.connect();
		}
		if (mGoogleApiClient.isConnected())
		{
			startLocationUpdates();

		}
	}

	private boolean checkPlayServices()
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		if (resultCode != ConnectionResult.SUCCESS)
		{
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
			{
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else
			{
				Toast.makeText(activity,"This device is not supported.", Toast.LENGTH_LONG).show();
			}
			return false;
		}
		return true;
	}
	protected synchronized void buildGoogleApiClient()
	{
		mGoogleApiClient = new GoogleApiClient.Builder(activity).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
	}
	protected void createLocationRequest()
	{
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
	}
	protected void startLocationUpdates() {
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	}

	protected void stopLocationUpdates()
	{
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}



}
