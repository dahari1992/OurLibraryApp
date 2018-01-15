package com.example.matan.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadMe extends AppCompatActivity implements View.OnClickListener {


    Button back;
    TextView read_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_me_layout);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
        read_me = (TextView) findViewById(R.id.read_me_text);
        read_me.setText("This application helps to manage the library store, providing help to customers" +
                "and to employees. The application let the user various actions ,such as" +
                "search books in the library and to return books." +
                "It also have the action to track books update buffer alerts and more...");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){

            case R.id.back:
                Intent in = new Intent(this,MainActivity.class);
                startActivity(in);
                break;

        }
    }
}

