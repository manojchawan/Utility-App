package com.manoj.jarvis;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private TextView resultTEXT; //for voice result

    private ZXingScannerView mScannerView; //for scanner

    //for LED
    ImageButton imageButton;
    Camera camera;
    Camera.Parameters parameters;
    boolean isflash = false;
    boolean isOn= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTEXT =(TextView)findViewById(R.id.TVresult);

        imageButton = (ImageButton)findViewById(R.id.imageButton2); //led btn

        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera = Camera.open();
            parameters = camera.getParameters();
            isflash = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isflash)
                {
                    if(!isOn)
                    {
                        imageButton.setImageResource(R.drawable.on);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn= true;
                    }
                    else
                    {
                        imageButton.setImageResource(R.drawable.off);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn= false;
                    }
                }
                else
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage("LED is not available on this Device.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null)
        {
            camera.release();
            camera = null;
        }
    }

    public void onButtonClick(View v) //for voice
    {
        if(v.getId() == R.id.imageButton)
        {
            speachInput();
        }
    }

    public void speachInput()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");

        try {
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(MainActivity.this, "Sorry! Your Device Dosent Support Speech language!", Toast.LENGTH_LONG).show();
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
                    resultTEXT.setText(res.get(0));
                }
            break;
        }
    }

    public void onQRButtonClick(View v) //for Qr scanning
    {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

   /* @Override
    protected void onPause() {
        //Pause QR-Reading
        super.onPause();
        mScannerView.stopCamera();
    }*/

    @Override
    public void handleResult(Result result) {
        //handling QR result
        Log.w("handleResult",result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //resume scan
        mScannerView.resumeCameraPreview(this);
    }
}
