package com.rxhelper.rxhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class prescriptionRecyclerViewAdapter extends RecyclerView.Adapter<prescriptionRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "prescripRecViewAdapter";

    private ArrayList<Prescription> mPrescriptions;
    private Context mContext;
    private Fragment mFragmentRef;

    public prescriptionRecyclerViewAdapter(ArrayList<Prescription> prescriptions, Context context, Fragment fragmentRef) {
        mPrescriptions = prescriptions;
        mContext = context;
        mFragmentRef = fragmentRef;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mPrescriptions.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.prescriptionName.setText(mPrescriptions.get(i).name);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mPrescriptions.get(i).name);

                Intent intent = new Intent(mContext, prescriptionEditor.class);
                intent.putExtra("PRESCRIPTIONEDIT", mPrescriptions.get(i));
                mFragmentRef.startActivityForResult(intent, PrescriptionTableView.REQ_CODE_PRESCRIPTIONEDIT);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView prescriptionName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            prescriptionName = itemView.findViewById(R.id.prescriptionName);
            parentLayout = itemView.findViewById(R.id.prescriptionRelativeLayout);
        }
    }
}
