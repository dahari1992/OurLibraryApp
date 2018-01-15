













package com.example.matan.library;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/*
MainActivity class used to login tha app or view readme file
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    //vars
    private Button login, read_me;
    private Boolean main_success;
    private EditText mainPass, mainUser;
    private FirebaseDatabase db;
    private DatabaseReference myUserRef ,myAdminRef , myEmpRef;
    private Map<String, String> myMap, adminMap, employMap;
    private String reg_user;
    private Vector<User> vectorUser, vectorAdmin , vectorEmploy;
    User admin;
    FirebaseAnalytics mFirebaseAnalytics;




    public String getLoginString(){
        return String.valueOf(R.string.login);
    }

    /**
     * initialize parameters
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        myUserRef = db.getReference("users");
        myAdminRef = db.getReference("Admin");
        myEmpRef = db.getReference("employ");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        main_success = false;
        myMap = new HashMap<String, String>();
        adminMap = new HashMap<String, String>();
        employMap = new HashMap<String, String>();
        vectorUser = new Vector<>();
        vectorAdmin = new Vector<>();
        vectorEmploy = new Vector<>();
        reg_user = getIntent().getStringExtra("username");
        updateMapAndVectors();
        mainPass = (EditText) findViewById(R.id.main_password);
        mainUser = (EditText) findViewById(R.id.main_username);
        mainUser.setText(reg_user);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        read_me = (Button)findViewById(R.id.read_me);
        read_me.setOnClickListener(this);
    }

    /**
     * onclick func for buttons login and readme
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                checkFields();
                if (searchUserInMap() && main_success) {
                    for (int i = 0; i <vectorAdmin.size(); i++){
                        if(String.valueOf(mainUser.getText()).equals(vectorAdmin.elementAt(i).getUsername())){
                            setDialogLogin2();

                        }
                        else{
                            setDialogLogin();

                        }
                    }



                }

               //user dose't exist in database
                else if (!searchUserInMap() && main_success) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("user does not exist please check with Admin your details");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("Back", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    mainUser.setText("");
                    mainPass.setText("");
                }
                break;

            case R.id.read_me:
                startActivity(new Intent(this, ReadMe.class));
                break;


        }

    }


    /**
     * @param username
     * @return user that connected
     */
    public User checkWhichUserConnected(String username) {
        for (int i = 0; i < vectorUser.size(); i++) {
            if (username.equals(vectorUser.elementAt(i).getUsername())) {
                return vectorUser.elementAt(i);
            }
        }
        for (int i = 0; i < vectorAdmin.size(); i++) {
            if (username.equals(vectorAdmin.elementAt(i).getUsername())) {
                return vectorAdmin.elementAt(i);
            }
        }

        for (int i = 0; i < vectorEmploy.size(); i++) {
            if (username.equals(vectorEmploy.elementAt(i).getUsername())) {
                return vectorEmploy.elementAt(i);
            }
        }
        return null;
    }

    /**
     * go to new activity
     * if number = 1 - user
     * if number = 0 - admin
     **/
    public void getintoActivity(int number) {
        if (number == 1) {
            User user = checkWhichUserConnected(mainUser.getText().toString());
            MemoryPrefrences memory = new MemoryPrefrences(this);
            memory.saveUser(user, new Callback<Boolean>() {
                @Override
                public void onSuccess(Boolean object) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("username", String.valueOf(mainUser.getText()));
                    startActivity(intent);
                }

                @Override
                public void onError(Boolean object) {
                    Toast.makeText(getApplicationContext(), "error saving", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            User user = checkWhichUserConnected(mainUser.getText().toString());
            MemoryPrefrences memory = new MemoryPrefrences(this);
            memory.saveUser(user, new Callback<Boolean>() {
                @Override
                public void onSuccess(Boolean object) {
                    Intent intent = new Intent(getApplicationContext(), AdminMenu.class);
                    intent.putExtra("username", String.valueOf(mainUser.getText()));
                    startActivity(intent);
                }

                @Override
                public void onError(Boolean object) {
                    Toast.makeText(getApplicationContext(), "error saving", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void setDialogLogin2() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Login success")
                .setMessage("Welcome Admin");
        dialog.setPositiveButton("Let's read!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                getintoActivity(0);
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
        final Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
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
                login.removeCallbacks(runnable);
            }
        });
        login.postDelayed(runnable, 60000);
    }

    /**
     * alert suitable message
     **/
    public void setDialogLogin() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Login success")
                .setMessage("Welcome to the Library");
        dialog.setPositiveButton("Let's read!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                getintoActivity(1);
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
        final Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
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
                login.removeCallbacks(runnable);
            }
        });
        login.postDelayed(runnable, 60000);
    }

    /**
     * alert message wrong password or username
     **/
    private void alertError() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("wrong password or username");
        dlgAlert.setTitle("Error Message...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    /**
     * check if username and password field's are full else error
     */
    private void checkFields() {
        String newUserName = mainUser.getText().toString();
        String newPass = mainPass.getText().toString();


        if (newPass.equals("") || newUserName.equals("")) {
            alertError();
        } else {
            main_success = true;
        }
    }

    /**
     * the function updated all the maps and vectors directly from database
     */
    private void updateMapAndVectors() {
        myEmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildUserName = dataSnapshot1.getKey();
                    String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                    String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                    String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                    employMap.put(myChildUserName, myChildPass);
                    User user = new User(myChildUserName, myChildPass, myChildEmail, myChildPhone, myChildAddress);
                    vectorEmploy.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getKey();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildUserName = dataSnapshot1.getKey();
                    String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                    String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                    String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                    myMap.put(myChildUserName, myChildPass);
                    User user = new User(myChildUserName, myChildPass, myChildEmail, myChildPhone, myChildAddress);
                    vectorUser.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myAdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getKey();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildUserName = dataSnapshot1.getKey();
                    String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                    String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                    String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                    adminMap.put(myChildUserName, myChildPass);
                    User user = new User(myChildUserName, myChildPass, myChildEmail, myChildPhone, myChildAddress);
                    vectorAdmin.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * @return true if the user exist
     * by username and password
     */
    private boolean searchUserInMap() {
        String newPass = String.valueOf(mainPass.getText());
        String newUserName = String.valueOf(mainUser.getText());
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            if (entry.getKey().equals(newUserName) && entry.getValue().equals(newPass)) {
                return true;
            }
        }

        for (Map.Entry<String, String> entry : adminMap.entrySet()) {
            if (entry.getKey().equals(newUserName) && entry.getValue().equals(newPass)) {
                return true;
            }
        }

        for (Map.Entry<String, String> entry : employMap.entrySet()) {
            if (entry.getKey().equals(newUserName) && entry.getValue().equals(newPass)) {
                return true;
            }
        }
        return false;
    }
}

