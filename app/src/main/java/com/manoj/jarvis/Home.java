package com.manoj.jarvis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Home extends AppCompatActivity {

    ViewGroup MyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MyLayout = (ViewGroup) findViewById(R.id.MyLayout);

        MyLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener(){
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        MoveToMenu(view);
                        return true;
                    }
                }
        );
    }
    public void MoveToMenu(View v)
    {
        Intent i = new Intent(this,MenuActivity.class);
        startActivity(i);
    }
}
