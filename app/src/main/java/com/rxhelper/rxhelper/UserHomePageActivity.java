package com.rxhelper.rxhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
