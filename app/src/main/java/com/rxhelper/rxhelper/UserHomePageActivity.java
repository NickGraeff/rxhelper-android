package com.rxhelper.rxhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class UserHomePageActivity extends AppCompatActivity {

    private static final String TAG = "UserHomePage";

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
