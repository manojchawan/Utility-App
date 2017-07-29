package com.manoj.jarvis;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE=1;
    ImageView myPicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button myBtn = (Button) findViewById(R.id.PicBtn);
        myPicView = (ImageView)findViewById(R.id.PicImgView);


        if (!hasCamera())
        {
            myBtn.setEnabled(false); //Disable the "Take Pic" Button
        }
    }

    //CHECK IF PHONE HAS A CAMERA
    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //LAUNCHING CAMERA
    public void onPicBtnClick(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);  // TAKE A PIC AND PASS RESULT TO " onActivityResult "
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            //GET THE PHOTO
            Bundle bundleData = data.getExtras();
            Bitmap photo = (Bitmap) bundleData.get("data");
            myPicView.setImageBitmap(photo);

        }
    }
}
