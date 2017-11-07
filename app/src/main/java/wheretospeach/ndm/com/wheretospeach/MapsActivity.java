package wheretospeach.ndm.com.wheretospeach;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationListObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendSuggestObject;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.SharedPrefence;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.googlemap.ActionLocation;
import wheretospeach.ndm.com.wheretospeach.googlemap.GetCurrentLocation;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncBitmapResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncBitmapResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.recievers.AlarmReceiver;
import wheretospeach.ndm.com.wheretospeach.service.StartAlarmForautoTracking;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,ActionLocation,AsyncBitmapResult,AsyncResult,AdapterResponse {

    private GoogleMap mMap;
    GetCurrentLocation getlocation=null;
    public SharedPreferences sharedPreference;
    public SharedPreferences.Editor sharedPreferenceEdit;
    String profileImage;
    //LatLng latlng=null;
    private RecyclerView horizontal_recycler_view;
    String UserId=null;
    boolean isLocationget=false;
    TextView mCounter;
    private LinearLayout bottonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        bottonLayout = (LinearLayout) findViewById(R.id.botton_layout);



        boolean alarmUp = (PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp)
        {

            StartAlarmForautoTracking sarm = new StartAlarmForautoTracking();
            sarm.startTracking(this);
        }



        sharedPreference= SharedPrefence.getSharedPrefence(this);
        sharedPreferenceEdit=sharedPreference.edit();
        profileImage=sharedPreference.getString("PROFILE_IMAGE_URL","");

        getlocation=new GetCurrentLocation(this,this);
        getlocation.createGoogleApiClient();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        if (ConnectionDetector.isConnectingToInternet(this))
        {

             UserId=sharedPreference.getString("USER_ID", "");
             new HttpAsyncResult(null, WebUrl.USER_FRIEND_LIST+"?userId="+UserId, null, "FRIEND_LIST", this).execute();

        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }

        if (ConnectionDetector.isConnectingToInternet(this))
        {

            new HttpAsyncResult(this, WebUrl.USER_FRIEND_SUGGEST+"?userId="+UserId, null, "FRIEND_SUGGEST", this).execute();

        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void getLocation(Location location)
    {
        if(isLocationget==false)
        {

            isLocationget=true;
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            //mMap.addMarker(new MarkerOptions().anchor(0.5f, 1).snippet("Population: 4,627,300").position(latlng).title("Current Location").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.me))));
            new HttpAsyncBitmapResult(this, profileImage, null, "", this, latlng).execute();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getlocation.connectGoogleApiClient();
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        try
        {
            getlocation.disconnectGoogleApiClient();
        }
        catch (Exception e)
        {

        }
    }




    @Override
    public void getResponse(Object key, Bitmap data,LatLng latLng)
    {

      try
      {
          FriendLocationListObject friendLocationListObject = (FriendLocationListObject) key;

          mMap.addMarker(new MarkerOptions().anchor(0.5f, 1).snippet(friendLocationListObject.getDateTime()).position(latLng).title(friendLocationListObject.getUserName()).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(data))));
      }
      catch (Exception e)
      {
          mMap.addMarker(new MarkerOptions().anchor(0.5f, 1).position(latLng).title("My Location").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(data))));
          e.printStackTrace();
      }
    }


    @Override
    public void getResponse(String key, String data)
    {

     try {
         if (key.equalsIgnoreCase("FRIEND_LIST")) {
             if (!data.equalsIgnoreCase("null")) {
                 Gson gson = new Gson();
                 FriendLocationListObject[] favoriteItems = gson.fromJson(data,
                         FriendLocationListObject[].class);

                 List<FriendLocationListObject> friendList = Arrays.asList(favoriteItems);
                 //filterListOfCustomer = new ArrayList<FriendLocationListObject>(friendList);

                 for (FriendLocationListObject friendLocationListObject : friendList) {
                     try {
                         LatLng latlng = new LatLng(Double.parseDouble(friendLocationListObject.getLatitude()), Double.parseDouble(friendLocationListObject.getLongitude()));
                         new HttpAsyncBitmapResult(this, friendLocationListObject.getProfileImageUrl(), null, friendLocationListObject, this, latlng).execute();
                     } catch (Exception e) {
                     }
                 }
             }
         }
         else if (key.equalsIgnoreCase("FRIEND_SUGGEST"))
         {
             try {
                 Gson gson = new Gson();
                 FriendSuggestObject[] favoriteItems = gson.fromJson(data,
                         FriendSuggestObject[].class);

                 List<FriendSuggestObject> friendsuggestList = Arrays.asList(favoriteItems);
                 //filterListOfCustomer = new ArrayList<FriendLocationListObject>(friendList);
                 FriendSuggestAdapter horizontalAdapter = new FriendSuggestAdapter(friendsuggestList, this, UserId, this);

                 LinearLayoutManager horizontalLayoutManagaer
                         = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                 horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                 horizontal_recycler_view.setAdapter(horizontalAdapter);
                 bottonLayout.setVisibility(View.VISIBLE);

             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
             new HttpAsyncResult(null, WebUrl.GET_FRIEND_REQUEST_LIST+"?friend_one="+UserId+"&count_friend_request=2", null, "FRIEND_REQUSET_COUNT", this).execute();
         }

         else if (key.equalsIgnoreCase("FRIEND_REQUSET_COUNT"))
         {
             try {
                int count=Integer.parseInt(data);
                 if(count>0)
                 {
                     mCounter.setVisibility(View.VISIBLE);
                     mCounter.setText(data + "");
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }

    }

    @Override
    public void adapterResponse(String serverData)
    {
      String data=serverData;
    }


    private Bitmap getMarkerBitmapFromView(Bitmap resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageBitmap(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.map_menu, menu);


        FrameLayout badgeLayout = (FrameLayout) menu.findItem(R.id.filter_cust_view).getActionView();

        ImageView badgeImageView = (ImageView) badgeLayout.findViewById(R.id.badgeImage);
        mCounter = (TextView) badgeLayout.findViewById(R.id.counter);
       // mCounter.setText(CartInitialization.cartItem+"");


        badgeImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
               if(mCounter.getVisibility()== View.VISIBLE) {
                   Intent intent = new Intent(MapsActivity.this, FriendRequestListActivity.class);
                   startActivity(intent);
               }

            }
        });



        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {

        Toast.makeText(this, "touch", Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(ev);
    }
}
