package com.example.matan.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

Button toInfo , get_location , upload;
String getUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        toInfo = (Button)findViewById(R.id.information);
        get_location = (Button)findViewById(R.id.location);
        toInfo.setOnClickListener(this);
        get_location.setOnClickListener(this);
        getUsername = getIntent().getStringExtra("username");
        upload = (Button)findViewById(R.id.upload_my_photo);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.information:
                Intent intent = new Intent(this,Information.class);
                intent.putExtra("username",getUsername);
                startActivity(intent);
                break;

            case R.id.location:
                Intent in2 = new Intent(this,MapsActivity.class);
                startActivity(in2);
                break;

            case R.id.upload_my_photo:
                Intent in3 = new Intent(this,UploadPhoto.class);
                startActivity(in3);
                break;
        }
    }
}
