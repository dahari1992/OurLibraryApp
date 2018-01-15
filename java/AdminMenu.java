package com.example.matan.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by matan on 10/01/2018.
 */

/***
 * AdminMenu activity - admin can create new user or go to main menu
 */
public class AdminMenu extends AppCompatActivity implements View.OnClickListener {

//vars
    Button to_menu ,createNewUser;
    EditText adminMessage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu_layout);
        adminMessage = (EditText)findViewById(R.id.AdminMessage);
        adminMessage.setText("Hello Admin");
        adminMessage.setEnabled(false);
        to_menu = (Button)findViewById(R.id.informationBt);
        createNewUser = (Button)findViewById(R.id.create_new_user);
        to_menu.setOnClickListener(this);
        createNewUser.setOnClickListener(this);
    }

    /***
     * create new user and go to menu buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.create_new_user:
                Intent create = new Intent(this, Register.class);
                startActivity(create);
                break;

            case R.id.informationBt:
                Intent in = new Intent(this, MenuActivity.class);
                startActivity(in);
                break;


        }
    }
}
