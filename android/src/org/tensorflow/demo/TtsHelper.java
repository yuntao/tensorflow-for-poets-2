package org.tensorflow.demo;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 */
public class TtsHelper {

    private final Context context;
    TextToSpeech tts;

    public TtsHelper(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    public void greet(String text) {
        tts.speak(getPrefix() + text + getSuffix(), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private String getPrefix() {
        return "Good afternoon ";
    }

    private String getSuffix() {
        return "!";
    }

}
