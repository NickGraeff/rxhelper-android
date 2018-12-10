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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void goToToS(View view) {

        final MainUser mainUser = MainUser.getMainUser();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        final String name = ((TextView)findViewById(R.id.editText)).getText().toString();
        final String email = ((TextView)findViewById(R.id.editText2)).getText().toString();
        final String pharmacyName = ((TextView)findViewById(R.id.editText3)).getText().toString();
        final String pharmacyPhoneNumber = ((TextView)findViewById(R.id.editText4)).getText().toString();
        final String password = ((TextView)findViewById(R.id.editText5)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // Sign up was a success

                    // Set reference to mainUser's Firebase account
                    mainUser.fbUserAccount = mAuth.getCurrentUser();
                    mainUser.fbUserAccount.updateProfile(new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build());

                    // Get a reference to Firebase's database and set some values
                    DatabaseReference fireDBUserRef = FirebaseDatabase.getInstance().getReference()
                            .child("/users/" + mAuth.getUid() + "/members/" + mAuth.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("pharmacyName", pharmacyName);
                    map.put("pharmacyPhoneNumber", pharmacyPhoneNumber);
                    fireDBUserRef.setValue(map);

                    // Set the primaryUser's information
                    mainUser.getPrimaryUser().dbKey = mAuth.getUid();

                    // Go to ToS screen
                    Intent intent = new Intent(SignUpActivity.this, ToSActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignUpActivity.this, "Account creation failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
