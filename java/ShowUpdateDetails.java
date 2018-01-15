package com.example.matan.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class ShowUpdateDetails extends AppCompatActivity implements View.OnClickListener {
    //vars
    private FirebaseDatabase db;
    private DatabaseReference myRef,myAdminRef,myEmployRef;
    private EditText details;
    private Vector<User> vector;
    private String userName, getUsername, getPassword, getEmail, getPhone, getAddress;
    private Button logout, menu;
    private HashMap<String , String> myMap;

    /**
     * @param savedState
     **/
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.updated_details);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users");
        myAdminRef = db.getReference("Admin");
        myEmployRef = db.getReference("employ");

        userName = getIntent().getStringExtra("usernameDetails");
        getUsername = getIntent().getStringExtra("username");
        getPassword = getIntent().getStringExtra("password");
        getEmail = getIntent().getStringExtra("email");
        getPhone = getIntent().getStringExtra("phone");
        getAddress = getIntent().getStringExtra("address");
        vector = new Vector<>();
        details.setText(userName);
        details.setEnabled(false);
        logout = (Button) findViewById(R.id.logoutUpdated);
        menu = (Button) findViewById(R.id.goToMenu);
        logout.setOnClickListener(this);
        menu.setOnClickListener(this);
        myMap = new HashMap<>();
    isExist();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.logoutUpdated:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;


            case R.id.goToMenu:
                getIntoACtivity();
                break;
        }
    }

    private boolean searchUserInMap() {
//        String newPass = String.valueOf(mainPass.getText());
//        String newUserName = String.valueOf(mainUser.getText());
        String username = String.valueOf(userName);
        String password = String.valueOf(getPassword);
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * go to after_login activity and pass the preferences memory
     */
    public void getIntoACtivity() {
        User user = new User(getUsername, getPassword, getEmail, getPhone, getAddress);
        MemoryPrefrences memory = new MemoryPrefrences(this);
        memory.saveUser(user, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Boolean object) {
                Toast.makeText(getApplicationContext(), "error saving", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isExist() {
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
    }
}
