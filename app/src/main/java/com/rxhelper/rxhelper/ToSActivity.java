package com.rxhelper.rxhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ToSActivity extends AppCompatActivity {

    // Use this to determine if the user accepted the ToS, if they didn't do not make their account
    private boolean mSelectedAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedAccept = false;
        setContentView(R.layout.activity_to_s);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void goToTutorial(View view) {
        mSelectedAccept = true;
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
