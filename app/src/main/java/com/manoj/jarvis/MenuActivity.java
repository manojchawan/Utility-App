package com.manoj.jarvis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void MoveToScanner(View v)
    {
        Intent i = new Intent(this, ScannerActivity.class);
        startActivity(i);
    }

    public void MoveToVoice(View v)
    {
        Intent iVoice = new Intent(this, VoiceActivity.class);
        startActivity(iVoice);
    }

    public void MoveToTorch(View v)
    {
        Intent iTorch = new Intent(this, TorchActivity.class);
        startActivity(iTorch);
    }

    public void MoveToCamera(View v)
    {
        Intent iCamera = new Intent(this,CameraActivity.class);
        startActivity(iCamera);
    }

    public void MoveToMaps(View v)
    {
        Intent iMap = new Intent(this,MapsActivity.class);
        startActivity(iMap);
    }


}
