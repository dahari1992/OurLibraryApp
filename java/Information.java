package com.example.matan.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/***
 * present info on  current user
 */
public class Information extends AppCompatActivity implements View.OnClickListener {

    Button back, update_details;
//    EditText userInfo;
    String getUsername, userDetails;
    TextView myInfo;
    private FirebaseDatabase db;
    private DatabaseReference myRef,myAdminRef,myEmployRef;

    /***
     *
     * @param savedInstanceState
     * init parameters
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updated_details);
        getUsername = getIntent().getStringExtra("username");
        userDetails = "";
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users");
        myAdminRef = db.getReference("Admin");
        myEmployRef = db.getReference("employ");
        back = (Button)findViewById(R.id.goToMenu);
        back.setOnClickListener(this);
        update_details = (Button)findViewById(R.id.update_details);
        update_details.setOnClickListener(this);
        myInfo = (TextView)findViewById(R.id.my_details);
        updatedMap();

    }

    /***
     *
     * @param v
     * connect to menu or update details activities
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){

            case R.id.goToMenu:
                Intent in = new Intent(this,MenuActivity.class);
                startActivity(in);
                break;
            case R.id.update_details:
                Intent in2 = new Intent(this,UpdateInfo.class);
                startActivity(in2);
                break;

        }
    }

    /***
     * update map from database by user,admin,employ
     * hold 3 references to each folder
     */
    private void updatedMap() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(getUsername)) {
                        String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                        String myChildUserName = dataSnapshot1.getKey();
                        String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                        String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                        String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                        userDetails = "Your Details:\n\n{\n\n username:" + myChildUserName+", \n"+ "password:"
                                + myChildPass+", \n" + "email:" +myChildEmail + ", \n" +"address:"
                                + myChildAddress + ", \n" +"phone:" + myChildPhone+"\n\n}";
                    }

                    myInfo.setText(userDetails);
                    myInfo.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myAdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(getUsername)) {
                        String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                        String myChildUserName = dataSnapshot1.getKey();
                        String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                        String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                        String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                        userDetails = "Your Details:\n\n{\n\n username:" + myChildUserName+", \n"+ "password:"
                                + myChildPass+", \n" + "email:" +myChildEmail + ", \n" +"address:"
                                + myChildAddress + ", \n" +"phone:" + myChildPhone+"\n\n}";
                    }

                    myInfo.setText(userDetails);
                    myInfo.setEnabled(false);

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
                    if (dataSnapshot1.getKey().equals(getUsername)) {
                        String myChildPass = String.valueOf(dataSnapshot1.child("password").getValue());
                        String myChildUserName = dataSnapshot1.getKey();
                        String myChildEmail = String.valueOf(dataSnapshot1.child("email").getValue());
                        String myChildPhone = String.valueOf(dataSnapshot1.child("phone").getValue());
                        String myChildAddress = String.valueOf(dataSnapshot1.child("address").getValue());
                        userDetails = "Your Details:\n\n{\n\n username:" + myChildUserName+", \n"+ "password:"
                                + myChildPass+", \n" + "email:" +myChildEmail + ", \n" +"address:"
                                + myChildAddress + ", \n" +"phone:" + myChildPhone+"\n\n}";
                    }

                    myInfo.setText(userDetails);
                    myInfo.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
