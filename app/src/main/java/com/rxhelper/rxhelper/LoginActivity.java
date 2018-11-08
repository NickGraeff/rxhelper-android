package com.rxhelper.rxhelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToWelcome(View view) {

        final MainUser mainUser = MainUser.getMainUser();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(((TextView)findViewById(R.id.editText7)).getText().toString(), ((TextView)findViewById(R.id.editText8)).getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in was a success

                            // Set reference to mainUser's Firebase account
                            mainUser.fbUserAccount = mAuth.getCurrentUser();

                            // Get a reference to Firebase's database and set some values
                            final DatabaseReference fireDBUserRef = FirebaseDatabase.getInstance().getReference().child("/users/" + mAuth.getUid() + "/members/" + mAuth.getUid());
                            fireDBUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String name = (String) dataSnapshot.child("name").getValue();
                                    String pharmacyName = (String) dataSnapshot.child("pharmacyName").getValue();
                                    String pharmacyPhoneNumber = (String) dataSnapshot.child("pharmacyPhoneNumber").getValue();
                                    String key = mAuth.getUid();

                                    // Create a member with the information for the primaryUser
                                    Member member = new MemberBuilder()
                                            .setName(name)
                                            .setKey(key)
                                            .setPharmacyName(pharmacyName)
                                            .setPharmacyPhoneNumber(pharmacyPhoneNumber)
                                            .build();
                                    mainUser.setPrimaryUser(member);

                                    // Go to the welcome screen
                                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    System.out.println(firebaseError.toString());
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
