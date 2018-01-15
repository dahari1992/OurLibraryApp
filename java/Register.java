package com.example.matan.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;





import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/***
 * register user in database
 */
public class Register extends AppCompatActivity implements View.OnClickListener {
    //vars
    public static final int GET_FROM_GALLERY = 3;
    private   Map<String ,String> myMap,myMapEmp,myAdminMap;
    private Button reg_submit , reg_submit_emp;
    private EditText reg_pass,reg_username,reg_phone,reg_address,reg_email;
    private Boolean reg_success , toast;
    //connect to firebase
    private FirebaseDatabase db;
    private DatabaseReference myRef, myRefEmp ,myRefAdmin;
    private User user;


    /**
     *
     * @param savedState
     */
    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.register_activity);
        toast = reg_success = false;
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users");
        myRefEmp = db.getReference("employ");
        myRefAdmin = db.getReference("Admin");

        myMap = new HashMap<String, String>();
        myMapEmp = new HashMap<String, String>();
        myAdminMap = new HashMap<String, String>();
        reg_pass = (EditText)findViewById(R.id.reg_password);
        reg_username = (EditText)findViewById(R.id.myText) ;
        reg_phone = (EditText)findViewById(R.id.reg_phone);
        reg_email = (EditText)findViewById(R.id.reg_email);
        reg_address = (EditText)findViewById(R.id.reg_address);
        reg_submit = (Button) findViewById(R.id.reg_submit);
        reg_submit.setOnClickListener(this);
        reg_submit_emp = (Button)findViewById(R.id.reg_submit_emp);
        reg_submit_emp.setOnClickListener(this);
        updateMap();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.reg_submit:
                createNewUser();
                break;

            case R.id.reg_submit_emp:
                createNewEmploy();
                break;

        }


        if(reg_success) {
            setDialogRegister();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Context context = getApplicationContext();
                CharSequence text = "Photo uploaded successfully!";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context,text,duration).show();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *  Alert error pop-up for wrong username or password
     */
    private void alertError(int premission) {
        if (premission == 1) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("wrong password or username");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle("User is already exist");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }


    /**
     * create new user and add it to the firebase ref
//     */
    private void createNewUser() {
        String newPass = reg_pass.getText().toString();
        String newUserName = reg_username.getText().toString();
        String newEmail = reg_email.getText().toString();
        String newAddress = reg_address.getText().toString();
        String newPhone = reg_phone.getText().toString();
        if (newPass.equals("") || newUserName.equals("")) {
            alertError(1);
        }
         else if (searchUserInMap()) {
            alertError(0);
            reg_username.setText("");
            reg_pass.setText("");
        }
            else {
            reg_success = true;
            user = new User(newUserName, newPass ,newEmail,newPhone,newAddress);
            myRef.child(newUserName).setValue(user);
        }
    }

    private void createNewEmploy() {
        String newPass = String.valueOf(reg_pass.getText());
        String newUserName = String.valueOf(reg_username.getText());
        String newEmail = String.valueOf(reg_email.getText());
        String newAddress = String.valueOf(reg_address.getText());
        String newPhone = String.valueOf(reg_phone.getText());
        if (newPass.equals("") || newUserName.equals("")) {
            alertError(1);
        }
        else if (searchUserInMap()) {
            alertError(0);
            reg_username.setText("");
            reg_pass.setText("");
        }
        else {
            reg_success = true;
            user = new User(newUserName, newPass ,newEmail,newPhone,newAddress);
            myRefEmp.child(newUserName).setValue(user);
        }
    }

    /**
     *read users from the database and insert them into hashmap by key and value
     */
    private void updateMap(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildPass =  String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildUserName = dataSnapshot1.getKey();
                    myMap.put(myChildUserName,myChildPass);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRefAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildPass =  String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildUserName = dataSnapshot1.getKey();
                    myAdminMap.put(myChildUserName,myChildPass);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRefEmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildPass =  String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildUserName = dataSnapshot1.getKey();
                    myMapEmp.put(myChildUserName,myChildPass);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    /**
     * @return true if the user is in the map
     */
    private boolean searchUserInMap(){
        String newUserName = reg_username.getText().toString();
        for(Map.Entry<String,String> entry : myMap.entrySet() ){
            if(entry.getKey().equals(newUserName)){
                return true;
            }
        }

        for(Map.Entry<String,String> entry : myMapEmp.entrySet() ){
            if(entry.getKey().equals(newUserName) ){
                return true;
            }
        }
        for(Map.Entry<String,String> entry : myAdminMap.entrySet() ){
            if(entry.getKey().equals(newUserName) ){
                return true;
            }
        }

        return false;
    }

    /**
     * pop-up message before getting to the next activity
     */
    public void setDialogRegister(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Register success")
                .setMessage("user " + String.valueOf(reg_username.getText()) + " was created successfully" );
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                getintoActivity();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
//
        final Handler handler  = new Handler() {
            @Override
            public void publish(LogRecord record) {}
            @Override
            public void flush() {}
            @Override
            public void close() throws SecurityException {}
        };
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                reg_submit.removeCallbacks(runnable);
            }
        });

        reg_submit.postDelayed(runnable,10000);
    }


            private void getintoActivity() {
                Intent intent = new Intent(getApplicationContext(),AdminMenu.class);
                intent.putExtra("username",user.getUsername().toString());
                startActivity(intent);

            }
        }

