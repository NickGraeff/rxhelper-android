package com.rxhelper.rxhelper;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    // Used to delay the application to 'welcome' the user
    private Handler myHandler;
    private Runnable myRunnable;

    // Used to handle user pressing back button while the handle is delaying the transition to
    // the Welcome screen
    private boolean mIsInForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set these up for the delay process
        myHandler = new Handler();
        myRunnable = new Runnable() {
            public void run() {
                if (mIsInForeground) {
                    goToWelcome();
                }
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsInForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIsInForeground = true;

        setContentView(R.layout.activity_tutorial);

        myHandler.removeCallbacksAndMessages(myRunnable);
        myHandler.postDelayed(myRunnable, 5000);
    }

    private void goToWelcome() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}