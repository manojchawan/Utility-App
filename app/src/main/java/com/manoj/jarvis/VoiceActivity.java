package com.manoj.jarvis;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceActivity extends AppCompatActivity {

    private TextView resultTXT; //FOR VOICE RESULT


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        resultTXT =(TextView)findViewById(R.id.VoiceResult);
    }

    public void onMicBtnClick(View v)
    {
        if(v.getId() == R.id.micBtn)
        {
            speechInput();
        }
    }

    public void speechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");


        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(VoiceActivity.this, "Sorry! Your Device Dosen't Support Speech language!", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent i)
    {
        super.onActivityResult(request_code,result_code,i);

        switch(request_code)
        {
            case 100:
                if(result_code== RESULT_OK && i !=null)
                {
                    ArrayList<String> res = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultTXT.setText(res.get(0));
                }
                break;
        }
    }
}
