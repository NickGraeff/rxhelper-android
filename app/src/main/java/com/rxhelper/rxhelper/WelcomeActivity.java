package com.rxhelper.rxhelper;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {

    // Used to delay the application to 'welcome' the user
    private Handler myHandler;
    private Runnable myRunnable;

    // Used to handle user pressing back button while the handle is delaying the transition to
    // the Welcome screen
    private boolean mIsInForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the screen
        setContentView(R.layout.activity_welcome);

        // Set the textView to welcome the user
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("Welcome " + MainUser.getMainUser().fbUserAccount.getDisplayName());

        // Begin the delay process
        myHandler = new Handler();
        myRunnable = new Runnable() {
            public void run() {
                if (mIsInForeground) {
                    goToUserHomePage();
                }
            }
        };
    }

    @Override protected void onPause() {
        super.onPause();

        mIsInForeground = false;
    }

    @Override protected void onResume() {
        super.onResume();

        mIsInForeground = true;

        myHandler.removeCallbacksAndMessages(myRunnable);
        myHandler.postDelayed(myRunnable, 500);
    }

    private void goToUserHomePage() {
        Intent intent = new Intent(this, UserHomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void retrieveUserInformationFromFirebase() {

        // Get a reference to the MainUser
       final MainUser mainUser = MainUser.getMainUser();


        DatabaseReference fireDBUserRef = FirebaseDatabase.getInstance().getReference().child("/users/"
                + mainUser.fbUserAccount.getUid() + "/members/" + mainUser.fbUserAccount.getUid());
        fireDBUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Loop through all the data in FireBase and set the corresponding information in the
                // MainUser object
                for(DataSnapshot data1 : dataSnapshot.getChildren()) {
                    for(DataSnapshot data2 : data1.getChildren()) {

                        final Object name = data2.child("name");

                        if (data2.getKey() == mainUser.fbUserAccount.getUid()) {
                            mainUser.getPrimaryUser().name = name.toString();
                            mainUser.getPrimaryUser().dbKey = data2.getKey();
                        } else {

                            final Member member = new MemberBuilder()
                                    .setName(name == null ? "" : name.toString())
                                    .setKey(data2.getKey())
                                    .build();
                            mainUser.subUsers.add(member);
                        }
                    }
                }

                // Pharmacy Phone Number and Name are located on the first level of the hierarchy
                Object pharmacyName = dataSnapshot.child("pharmacyName").getValue();
                if (pharmacyName != null) {
                    mainUser.pharmacyName = pharmacyName.toString();
                }

                Object pharmacyPhoneNumber = dataSnapshot.child("pharmacyPhoneNumber").getValue();
                if (pharmacyPhoneNumber != null) {
                    mainUser.pharmacyPhoneNumber = pharmacyPhoneNumber.toString();
                }

                mainUser.currentUser = mainUser.getPrimaryUser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
