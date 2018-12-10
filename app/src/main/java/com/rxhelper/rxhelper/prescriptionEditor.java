package com.rxhelper.rxhelper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class prescriptionEditor extends AppCompatActivity {

    public static final String TAG = prescriptionEditor.class.getSimpleName();

    public static final String prescriptionKey = "PRESCRIPTION";

    private Prescription mPrescription;

    private EditText mNameField;
    private EditText mDosageField;
    private EditText mRemainingDosesField;

    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_editor);

        getIncomingIntent();
        setUpSaveButton();
    }

    private void setTextFields() {
        mNameField = (EditText) findViewById(R.id.nameTextView);
        mDosageField = (EditText) findViewById(R.id.dosageTextView);
        mRemainingDosesField = (EditText) findViewById(R.id.remainingDosesTextView);

        if (getIntent().hasExtra("PRESCRIPTIONEDIT")) {
            mNameField.setText(mPrescription.name);
            mDosageField.setText(mPrescription.dosage);
            mRemainingDosesField.setText(Integer.toString(mPrescription.remainingDoses));
        }

    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");


        if (getIntent().hasExtra("PRESCRIPTIONEDIT")) {
            mPrescription = getIntent().getParcelableExtra("PRESCRIPTIONEDIT");
        }

        setTextFields();
    }

    private void setUpSaveButton() {

        mSaveButton = (Button) findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("PRESCRIPTIONADD")) {
                    Intent returnIntent = new Intent();
                    mPrescription = new PrescriptionBuilder()
                            .setName(mNameField.getText().toString())
                            .setDosage(mDosageField.getText().toString())
                            .setRemainingDoses(Integer.parseInt(mRemainingDosesField.getText().toString()))
                            .build();
                    returnIntent.putExtra("PRESCRIPTIONADD", mPrescription);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else if (getIntent().hasExtra("PRESCRIPTIONEDIT")) {
                    Intent returnIntent = new Intent();
                    mPrescription.name = mNameField.getText().toString();
                    mPrescription.dosage = mDosageField.getText().toString();
                    mPrescription.remainingDoses = Integer.parseInt(mRemainingDosesField.getText().toString());
                    returnIntent.putExtra("PRESCRIPTIONEDIT", mPrescription);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
