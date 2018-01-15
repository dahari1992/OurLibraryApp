package com.example.matan.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/***
 * update details of current user
 */
public class UpdateInfo extends AppCompatActivity implements View.OnClickListener {

    private Button update, logout;
    private EditText updateMyPass, updateMyUsername , updateMyPhone, updateMyEmail,updateMyAddress;
    private FirebaseDatabase db;
    private DatabaseReference myRef , myAdminRef, myEmployRef ;
    private String userDetails,myUsername,myPassword,myAddress,myPhone,myEmail;
    private User user;
    private HashMap<String,String> myMap , myEmployMap;
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        update = (Button) findViewById(R.id.updateMe);
        update.setOnClickListener(this);
        updateMyUsername = (EditText) findViewById(R.id.myText);
        updateMyPass = (EditText) findViewById(R.id.update_password);
        updateMyPhone = (EditText) findViewById(R.id.update_phone);
        updateMyAddress = (EditText) findViewById(R.id.update_address);
        updateMyEmail = (EditText) findViewById(R.id.update_email);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        userDetails = "";
        myMap = new HashMap<>();
        myEmployMap = new HashMap<>();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users");
        myAdminRef = db.getReference("Admin");
        myEmployRef = db.getReference("employ");

        MemoryPrefrences memoryPrefrences = new MemoryPrefrences(this);
        memoryPrefrences.loadUser(new Callback<User>() {
            @Override
            public void onSuccess(User user) {
                setUserDetails(user);
            }

            @Override
            public void onError(User object) {
                //TODO toast
            }
        });

        updateMap();
    }

    /***
     * update map from database
     * hold 3 references admin user employ
     */
    private void updateMap() {
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
                    myMap.put(myChildUserName, myChildPass);
                    User user = new User(myChildUserName, myChildPass, myChildEmail, myChildPhone, myChildAddress);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myEmployRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String myChildUserName = dataSnapshot1.getKey();
                    String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                    String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                    String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                    myEmployMap.put(myChildUserName, myChildPass);
                    User user = new User(myChildUserName, myChildPass, myChildEmail, myChildPhone, myChildAddress);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
}

    /***
     *
     * @return true if user is in map else fasle
     */
    private boolean searchUserInMap() {
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            if (entry.getKey().equals(String.valueOf(updateMyUsername.getText())) &&
                    entry.getValue().equals(String.valueOf(updateMyPass.getText()))) {
                return true;
            }
        }
        return false;
    }
    /***
     *
     * @return true if employ is in map else fasle
     */
    private boolean searchEmployInMap() {
        for (Map.Entry<String, String> entry : myEmployMap.entrySet()) {
            if (entry.getKey().equals(String.valueOf(updateMyUsername.getText())) &&
                    entry.getValue().equals(String.valueOf(updateMyPass.getText()))) {
                return true;
            }

        }
        return false;
    }
    /**
     * @param user
     * set user details
     * **/
    private void setUserDetails(User user) {
        updateMyUsername.setText(user.getUsername());
        updateMyUsername.setEnabled(false);
        updateMyPass.setText(user.getPassword());
        updateMyPass.setEnabled(false);
        updateMyEmail.setText(user.getEmail());
        updateMyAddress.setText(user.getAddress());
        updateMyPhone.setText(user.getPhone());
        userDetails = "Your Details:\n\n{\n\n username:" + user.getUsername()+", \n"+ "password:"
                + user.getPassword()+", \n" + "email:" +user.getEmail() + ", \n" +"address:"
                + user.getAddress() + ", \n" +"phone:" + user.getPhone()+"\n\n}";
    }

    /**
     * update user details
     * **/
    private void updateUserInDataBase() {
        String username = updateMyUsername.getText().toString();
        String password = updateMyPass.getText().toString();
        String updateEmail = updateMyEmail.getText().toString();
        String updateAddress = updateMyAddress.getText().toString();
        String updatePhone = updateMyPhone.getText().toString();
        user = new User(username, password, updateEmail, updatePhone, updateAddress);
        if (searchUserInMap()) {
            myAdminRef.child(username).setValue(user);
            userDetails = "Your Details:\n\n{\n\nusername:" + user.getUsername() + ", \n\n" + "password:"
                    + user.getPassword() + ", \n\n"
                    + "email:" + user.getEmail() + ", \n\n" + "address:" + user.getAddress()
                    + ", \n\n" + "phone:" + user.getPhone() + "\n\n}";
            myUsername = username;
            myPassword = password;
            myEmail = updateEmail;
            myAddress = updateAddress;
            myPhone = updatePhone;
        }
        else if(searchEmployInMap()){
            myEmployRef.child(username).setValue(user);
            userDetails = "Your Details:\n\n{\n\nusername:" + user.getUsername() + ", \n\n" + "password:"
                    + user.getPassword() + ", \n\n"
                    + "email:" + user.getEmail() + ", \n\n" + "address:" + user.getAddress()
                    + ", \n\n" + "phone:" + user.getPhone() + "\n\n}";
            myUsername = username;
            myPassword = password;
            myEmail = updateEmail;
            myAddress = updateAddress;
            myPhone = updatePhone;
        }
        else {

            myRef.child(username).setValue(user);
            userDetails = "Your Details:\n\n{\n\nusername:" + user.getUsername() + ", \n\n" + "password:"
                    + user.getPassword() + ", \n\n"
                    + "email:" + user.getEmail() + ", \n\n" + "address:" + user.getAddress()
                    + ", \n\n" + "phone:" + user.getPhone() + "\n\n}";
            myUsername = username;
            myPassword = password;
            myEmail = updateEmail;
            myAddress = updateAddress;
            myPhone = updatePhone;
        }
    }

    /***
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.updateMe:
                updateUserInDataBase();
                Intent intent = new Intent(this,MenuActivity.class);
                intent.putExtra("usernameDetails",userDetails);
                intent.putExtra("username",myUsername);
                intent.putExtra("password",myPassword);
                intent.putExtra("email",myEmail);
                intent.putExtra("phone",myPhone);
                intent.putExtra("address",myAddress);
                startActivity(intent);
                break;

            case R.id.logout:
                clearUserName(this);
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    /**
     * clear memory of preferences
     * **/
    public void clearUserName(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences
                (context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();

    }
}
