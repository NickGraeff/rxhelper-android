package com.rxhelper.rxhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class PrescriptionTableView extends Fragment {

    private static final String TAG = "PrescriptionTableView";

    public static final int REQ_CODE_PRESCRIPTIONADD = 1;
    public static final int REQ_CODE_PRESCRIPTIONEDIT = 2;

    public FloatingActionButton addButton;

    //vars
    private ArrayList<Prescription> mPrescriptions = new ArrayList<>();
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_prescription_table_view, container, false);
        initRecyclerView();

        final Context tempContext = mView.getContext();

        addButton = (FloatingActionButton) mView.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "fab onClick: onClick");

                // Transition to the prescriptionEditor screen
                Intent intent = new Intent(tempContext, prescriptionEditor.class);
                intent.putExtra("PRESCRIPTIONADD", 1);
                startActivityForResult(intent, REQ_CODE_PRESCRIPTIONADD);
            }
        });
        return mView;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");

        // Setup RecyclerView
        RecyclerView recyclerView = mView.findViewById(R.id.prescriptionRecyclerView);
        prescriptionRecyclerViewAdapter adapter = new prescriptionRecyclerViewAdapter(mPrescriptions, mView.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        // Fill the RecyclerView
        getPrescriptionsFromFirebase();
    }

    private void getPrescriptionsFromFirebase() {
        final MainUser mainUser = MainUser.getMainUser();
        DatabaseReference fireDBUserRef = FirebaseDatabase.getInstance().getReference().child("/users/"
                + mainUser.getPrimaryUser().dbKey + "/members/" + mainUser.currentUser.dbKey + "/prescriptions");

        fireDBUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Loop through all the data in FireBase and set the corresponding information in the
                // MainUser object
                for (DataSnapshot data1 : dataSnapshot.getChildren()) {

                    final Object name = data1.child(Prescription.PropertyKey.name).getValue();
                    final Object dosage = data1.child(Prescription.PropertyKey.dosage).getValue();
                    final Object remainingDoses = data1.child(Prescription.PropertyKey.remainingDoses).getValue();

                    final Prescription prescription = new PrescriptionBuilder()
                            .setName(name == null ? "" : name.toString())
                            .setDosage(dosage == null ? "" : dosage.toString())
                            .setRemainingDoses(remainingDoses == null ? 0 : Integer.parseInt(remainingDoses.toString()))
                            .setDBKey(data1.getKey())
                            .build();

                    mPrescriptions.add(prescription);

//                    for (DataSnapshot data2 : data1.child("alerts").getChildren()) {
//
//                    }
                }

                RecyclerView recyclerView = mView.findViewById(R.id.prescriptionRecyclerView);
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void savePrescriptionsToFirebase() {

        // Get a reference to Firebase's database and set some values
        MainUser mainUser = MainUser.getMainUser();
        DatabaseReference fireDBUserRef = FirebaseDatabase.getInstance().getReference().child("/users/"
                + mainUser.fbUserAccount.getUid() + "/members/" + mainUser.currentUser.dbKey + "/prescriptions/");

        for (int i = 0; i < mPrescriptions.size(); ++i) {
            DatabaseReference newPrescriptionReference;

            if (mPrescriptions.get(i).dbKey.equals("")) {
                newPrescriptionReference = fireDBUserRef.push();
                mPrescriptions.get(i).dbKey = newPrescriptionReference.getKey();
            } else {
                newPrescriptionReference = fireDBUserRef.child(mPrescriptions.get(i).dbKey);
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put(Prescription.PropertyKey.name, mPrescriptions.get(i).name);
            map.put(Prescription.PropertyKey.dosage, mPrescriptions.get(i).dosage);
            map.put(Prescription.PropertyKey.remainingDoses, mPrescriptions.get(i).remainingDoses);
            newPrescriptionReference.setValue(map);
        }

        RecyclerView recyclerView = mView.findViewById(R.id.prescriptionRecyclerView);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == REQ_CODE_PRESCRIPTIONADD) {

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                Prescription newPrescription = data.getParcelableExtra("PRESCRIPTIONADD");
                mPrescriptions.add(newPrescription);
                savePrescriptionsToFirebase();
            }

            // Else anything done on the edit screen can be thrown away

        } else if (requestCode == REQ_CODE_PRESCRIPTIONEDIT) {

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra("PRESCRIPTIONEDIT")) {
                    Prescription prescription = data.getParcelableExtra("PRESCRIPTIONEDIT");
                    for (int i = 0; i < mPrescriptions.size(); ++i) {
                        if (prescription.dbKey.equals(mPrescriptions.get(i).dbKey)) {
                            mPrescriptions.set(i, prescription);
                        }
                    }
                }
                savePrescriptionsToFirebase();
            }

            // Else anything done on the edit screen can be thrown away

        }
    }

}
