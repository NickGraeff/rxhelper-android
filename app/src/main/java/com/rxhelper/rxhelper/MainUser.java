package com.rxhelper.rxhelper;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Vector;

public class MainUser {

    private static MainUser MainUserInstance = null;

    private Member primaryUser; // This is a reference to the first user of subUsers
    public Member currentUser; // This is a reference to the user who's prescriptions are being accessed
    public Vector<Member> subUsers; // This holds the primaryUser and all subUsers of that user
    public FirebaseUser fbUserAccount; // Holds a reference to the user's FireBase account
    public String pharmacyName; // Holds the primaryUser's pharmacyName, entered at sign-up
    public String pharmacyPhoneNumber; // Holds the primaryUser's pharmacyPhoneNUmber, entered at sign-up

    private MainUser() {

        // This is not supposed to happen
        if (MainUserInstance != null) {
            System.out.println("This is a singleton class and it has been instantiated more than once.");
            System.exit(1);
        }

        // This is the 'real' part of the constructor
        subUsers = new Vector<Member>();
        subUsers.add(new MemberBuilder().build());
        primaryUser = subUsers.elementAt(0);
        currentUser = primaryUser;
        fbUserAccount = null;
    }

    public Member getPrimaryUser() {
        return primaryUser;
    }

    public static MainUser getMainUser() {
        if (MainUserInstance == null) {
            MainUserInstance = new MainUser();
        }
        return MainUserInstance;
    }
}
