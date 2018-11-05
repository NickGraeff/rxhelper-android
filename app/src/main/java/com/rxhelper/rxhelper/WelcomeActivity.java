package com.rxhelper.rxhelper;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        textView.setText("Welcome guy");

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
        myHandler.postDelayed(myRunnable, 5000);
    }

    private void goToUserHomePage() {
        Intent intent = new Intent(this, UserHomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
