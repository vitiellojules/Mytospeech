package com.example.mytospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(this, this);

        findViewById(R.id.btnSpeak).setOnClickListener(v -> speakText());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int langResult = textToSpeech.setLanguage(Locale.FRANCE); 

            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "O idioma não é suportado.", Toast.LENGTH_SHORT).show();
            } else {
                float speechRate = 1.0f;
                float pitch = 1.0f;
                textToSpeech.setSpeechRate(speechRate);
                textToSpeech.setPitch(pitch);


                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {

                    }

                    @Override
                    public void onError(String utteranceId) {

                    }
                });
            }
        } else {
            Toast.makeText(this, "Falha na inicialização do TextToSpeech.", Toast.LENGTH_SHORT).show();
        }
    }

    private void speakText() {
        if (textToSpeech != null && textToSpeech.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_AVAILABLE) {
            HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uniqueId");
            textToSpeech.speak("Bonjour, comment allez-vous, je pense tout bien", TextToSpeech.QUEUE_FLUSH, params);
        } else {
            Toast.makeText(this, "O TextToSpeech não está pronto para falar no idioma francês.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
