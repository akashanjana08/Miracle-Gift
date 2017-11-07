package wheretospeach.ndm.com.wheretospeach.speach.recognition;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Ndm-PC on 9/6/2016.
 */
public class TextToSpeechRecognition implements TextToSpeech.OnInitListener
{
    Context mContext;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static TextToSpeech tts;

    public   void speakOut(String text,Context mContext)
    {

        this.mContext=mContext;
        tts = new TextToSpeech(mContext, this);
        tts.setLanguage(Locale.ENGLISH);
        tts.setPitch(0.6f);
        tts.setSpeechRate(1);
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
                Toast.makeText(mContext, "This Language is not supported", Toast.LENGTH_LONG).show();
            } else {
                //btnSpeak.setEnabled(true);
                // speakOut("Please Make Some Voice");
                Toast.makeText(mContext,"Please Make Some Voice",Toast.LENGTH_LONG).show();
            }

        } else {
            // Log.e("TTS", "Initilization Failed!");
        }

    }

}
