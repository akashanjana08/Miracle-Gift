package wheretospeach.ndm.com.wheretospeach;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import wheretospeach.ndm.com.wheretospeach.database.FriendMasterTable;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;

public class MainActivity extends SpeachActionBar implements TextToSpeech.OnInitListener,AsyncResult
{

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static MainActivity mainActivity;
    public static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        setActionBarTitle("speak");
        if(!checkCameraPermission(this)) {
            permissions(this);
        }
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.ENGLISH);
        tts.setPitch(0.6f);
        tts.setSpeechRate(1);

        // hide the action bar
        //getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


        FriendMasterTable friendMasterTable =new FriendMasterTable(this);
        int size=friendMasterTable.getAllFriendMaster().size();
        Toast.makeText(this,size+"",Toast.LENGTH_LONG).show();

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));

                    String textvoice= result.get(0);

                    if(textvoice.length()>10)
                    {

                    if(textvoice.substring(0,8).equalsIgnoreCase("where is"))
                    {

                        speakOut(result.get(0));

                        String speakname = textvoice.substring(8, textvoice.length());

                        if (ConnectionDetector.isConnectingToInternet(MainActivity.this))
                        {
                            new HttpAsyncResult(MainActivity.this, WebUrl.GET_USER_LOCATION_URL+"?userName="+speakname.trim()+"&userId="+UserId, null, "DEVICE_REGISTER", MainActivity.this).execute();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        speakOut("Wrong Speak Please try again");
                    }

                    }
                    else
                    {
                        speakOut("Wrong Speak Please try again");
                    }

                }
                break;
            }

        }
    }



    public static  void speakOut(String text) {


        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
               // Log.e("TTS", "This Language is not supported");
                Toast.makeText(this,"This Language is not supported",Toast.LENGTH_LONG).show();
            } else {
                btnSpeak.setEnabled(true);
               // speakOut("Please Make Some Voice");
                Toast.makeText(this,"Please Make Some Voice",Toast.LENGTH_LONG).show();
            }

        } else {
           // Log.e("TTS", "Initilization Failed!");
        }

    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("DEVICE_REGISTER"))
        {
            Toast.makeText(this,data+"",Toast.LENGTH_LONG).show();
            if(data.length()>20)
            {
                speakOut("Please Wait While we find The Location");
            }
            else
            {
                speakOut("User Not For Try again");
            }
        }

    }


    public static void permissions(Context mContext)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (!android.provider.Settings.System.canWrite(mContext))
            {
                ((Activity)mContext).requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE
                        , android.Manifest.permission.CAMERA}, 4);

            }

        }
    }

    public static  boolean checkCameraPermission(Context mContext)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);


        if(permissionCheck==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}