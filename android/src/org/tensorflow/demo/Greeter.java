package org.tensorflow.demo;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.List;
import java.util.Locale;

/**
 */
public class Greeter {
    public static final float CONFIDENCE_TO_GREET = .7f;
    private static final long RECOGNITION_INTERVAL_MS = 1000;

    private final Context context;
    private TextToSpeech tts;

    private Classifier.Recognition lastRecognition;
    private long lastRecognitizedTimeMs;

    public Greeter(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void filterAndGreet(List<Classifier.Recognition> results) {
        for (Classifier.Recognition recognition : results) {
            if (recognition.getConfidence() > CONFIDENCE_TO_GREET) {

                long currTime = System.currentTimeMillis();
                if (currTime - lastRecognitizedTimeMs > RECOGNITION_INTERVAL_MS
                        && (lastRecognition == null || lastRecognition.getTitle() != recognition.getTitle())) {
                    tts.speak(getPrefix() + recognition.getTitle() + getSuffix(), TextToSpeech.QUEUE_FLUSH, null, null);

                    lastRecognitizedTimeMs = currTime;
                    lastRecognition = recognition;
                }
                break;
            }
        }
    }

    private String getPrefix() {
        return "Good afternoon ";
    }

    private String getSuffix() {
        return "!";
    }

}
