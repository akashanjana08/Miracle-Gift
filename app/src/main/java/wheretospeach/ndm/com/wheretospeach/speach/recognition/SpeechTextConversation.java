package wheretospeach.ndm.com.wheretospeach.speach.recognition;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import wheretospeach.ndm.com.wheretospeach.R;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;

/**
 * Created by Ndm-PC on 9/6/2016.
 */
public class SpeechTextConversation implements TextToSpeech.OnInitListener,AsyncResult
{
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static TextToSpeech tts;
    Context mContext;

   public void  onSpeech(Context mContext)
   {

       this.mContext=mContext;
       tts = new TextToSpeech(mContext, this);
       tts.setLanguage(Locale.ENGLISH);
       tts.setPitch(0.6f);
       tts.setSpeechRate(1);
       promptSpeechInput();
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mContext.getString(R.string.speech_prompt));
        try {
            ((Activity)mContext).startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(mContext,
                    mContext.getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public static  void speakOut(String text) {


        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }



    @Override
    public void onInit(int status)
    {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                // Log.e("TTS", "This Language is not supported");
                Toast.makeText(mContext,"This Language is not supported",Toast.LENGTH_LONG).show();
            } else {
                //btnSpeak.setEnabled(true);
                // speakOut("Please Make Some Voice");
                Toast.makeText(mContext,"Please Make Some Voice",Toast.LENGTH_LONG).show();
            }

        } else {
            // Log.e("TTS", "Initilization Failed!");
        }

    }

    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("DEVICE_REGISTER"))
        {
            Toast.makeText(mContext,data+"",Toast.LENGTH_LONG).show();
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


}
