package wheretospeach.ndm.com.wheretospeach;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wheretospeach.ndm.com.wheretospeach.facebookdeveloper.ReadHashCode;
import wheretospeach.ndm.com.wheretospeach.general.CalanderFormat;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.json.HashMapJson;
import wheretospeach.ndm.com.wheretospeach.permission.AndroidPermissions;
import wheretospeach.ndm.com.wheretospeach.recievers.AlarmReceiver;
import wheretospeach.ndm.com.wheretospeach.service.StartAlarmForautoTracking;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterUserActivity extends SpeachActionBar implements AsyncResult {

    // UI references.
    //private EditText mUserName,mobNumberet;
    String speakUserName,mobileNumber;
    private CallbackManager callbackManager;
    private TextView textView;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_user);

        getSupportActionBar().hide();
        // Set up the login form.
        setActionBarTitle("Register Speak UserName");
        //mUserName  = (EditText) findViewById(R.id.username);
        //mobNumberet= (EditText) findViewById(R.id.mobilenumbertextview);



        if(!AndroidPermissions.checkPermission(this))
        {
            AndroidPermissions.permissions(this);
        }

        //String hashCode=ReadHashCode.getFacebookRegisterHashCode(this,"wheretospeach.ndm.com.wheretospeach");

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
               // displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();



        //LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);

       // loginButton.setReadPermissions(Arrays.asList(
        //        "public_profile", "email", "user_birthday", "user_friends"));






    }


    public void onRegister(View view)
    {

       // speakUserName=mUserName.getText().toString().trim();
       // mobileNumber=mobNumberet.getText().toString().trim();

        if (ConnectionDetector.isConnectingToInternet(this))
        {
            onFblogin();
           // new HttpAsyncResult(this, WebUrl.USER_NAME_REGISTER_URL+"?speakUser="+speakUserName+"&userId="+UserId+"&mobile="+mobileNumber, null, "DEVICE_REGISTER", this).execute();
        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("DEVICE_REGISTER"))
        {

            sharedPreferenceEdit.putString("USER_Name", speakUserName);
            sharedPreferenceEdit.commit();


            Intent myintent=new Intent(this,MapsActivity.class);

            startActivity(myintent);


            finish();

        }

    }







//    private void displayMessage(Profile profile)
//    {
//        if(profile != null)
//        {
//            //textView.setText(profile.getName());
//            uri= profile.getProfilePictureUri(100, 100);
//            String name       =profile.getName();
//            String profileId  =profile.getId();
//
//            sharedPreferenceEdit.putString("USER_ID", profileId);
//            sharedPreferenceEdit.putString("PROFILE_IMAGE_URL", uri.toString());
//            sharedPreferenceEdit.commit();
//
//
//            String deviceId = Settings.Secure.getString(RegisterUserActivity.this.getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//
//
//            String appId=sharedPreference.getString("APPLICATION_ID","NA");
//
//
//            Map<String,String> userDetailJson= new HashMap<String,String>();
//            userDetailJson.put("speakUser",name);
//
//            userDetailJson.put("mobile", mobileNumber);
//            userDetailJson.put("profileuri",uri.toString());
//            userDetailJson.put("profileId",profileId);
//
//            userDetailJson.put("appid",appId);
//            userDetailJson.put("deviceId",deviceId);
//            userDetailJson.put("dateTime",CalanderFormat.getDateTimeCalender());
//
//
//
//
//            JSONObject jSONObject=HashMapJson.getJsonObject(userDetailJson);
//            JSONArray jsonarray= new JSONArray().put(jSONObject);
//            new HttpAsyncResult(RegisterUserActivity.this, WebUrl.DEVICE_REGISTER_URL, jsonarray.toString(), "DEVICE_REGISTER", RegisterUserActivity.this).execute();
//
//            //new HttpAsyncResult(RegisterUserActivity.this, WebUrl.USER_NAME_REGISTER_URL+"?speakUser="+name+"&userId="+UserId+"&mobile="+mobileNumber+"&profileuri="+uri.toString(), jsonarray.toString(), "DEVICE_REGISTER", RegisterUserActivity.this).execute();
//            //Toast.makeText(this,profile.getName(),Toast.LENGTH_LONG).show();
//            //Toast.makeText(this,profile.describeContents()+"",Toast.LENGTH_LONG).show();
//        }
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//
//    }









    // Private method to handle Facebook login and callback
    private void onFblogin()
    {
        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile","public_profile", "user_birthday", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                                //String str_email = json.getString("email");
                                                String profileId = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");
                                                String name = json.getString("name");
                                                String uri = null;


                                                JSONObject data = response.getJSONObject();
                                                if (data.has("picture"))
                                                {
                                                    uri = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                    // set profile image to imageview using Picasso or Native methods
                                                }

                                                ///////////////////////////////////////////////////////



            sharedPreferenceEdit.putString("USER_ID", profileId);
            sharedPreferenceEdit.putString("PROFILE_IMAGE_URL", uri);
            sharedPreferenceEdit.commit();


            String deviceId = Settings.Secure.getString(RegisterUserActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);


            String appId=sharedPreference.getString("APPLICATION_ID","NA");


            Map<String,String> userDetailJson= new HashMap<String,String>();
            userDetailJson.put("speakUser",name);

            userDetailJson.put("mobile", mobileNumber);
            userDetailJson.put("profileuri",uri.toString());
            userDetailJson.put("profileId",profileId);

            userDetailJson.put("appid",appId);
            userDetailJson.put("deviceId",deviceId);
            userDetailJson.put("dateTime",CalanderFormat.getDateTimeCalender());




            JSONObject jSONObject=HashMapJson.getJsonObject(userDetailJson);
            JSONArray jsonarray= new JSONArray().put(jSONObject);
            new HttpAsyncResult(RegisterUserActivity.this, WebUrl.DEVICE_REGISTER_URL, jsonarray.toString(), "DEVICE_REGISTER", RegisterUserActivity.this).execute();

            //new HttpAsyncResult(RegisterUserActivity.this, WebUrl.USER_NAME_REGISTER_URL+"?speakUser="+name+"&userId="+UserId+"&mobile="+mobileNumber+"&profileuri="+uri.toString(), jsonarray.toString(), "DEVICE_REGISTER", RegisterUserActivity.this).execute();
            //Toast.makeText(this,profile.getName(),Toast.LENGTH_LONG).show();
            //Toast.makeText(this,profile.describeContents()+"",Toast.LENGTH_LONG).show();

             /////////////////////////////////////////////////////////



                                            } catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email,first_name,last_name,gender,picture.width(100).height(100)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        //Log.d(TAG_CANCEL, "On cancel");
                        Toast.makeText(RegisterUserActivity.this, "On cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        //Log.d(TAG_ERROR, error.toString());
                        Toast.makeText(RegisterUserActivity.this, "onError", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

