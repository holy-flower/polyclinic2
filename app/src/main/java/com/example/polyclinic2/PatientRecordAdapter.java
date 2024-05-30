package com.example.polyclinic2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientRecordAdapter extends RecyclerView.Adapter<PatientRecordAdapter.ViewHolder> {

    private ArrayList<PatientRecord> mPatientRecords;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mRegisteredTimeTextView;
        private TextView mRegisteredDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.fio_user_list);
            mRegisteredTimeTextView = itemView.findViewById(R.id.time_list);
            mRegisteredDateTextView = itemView.findViewById(R.id.date_list);
            Log.d("patientRecord", "patientRecords in onBindViewHolder: " + mPatientRecords);
        }
    }

    public PatientRecordAdapter(ArrayList<PatientRecord> patientRecords) {
        mPatientRecords = patientRecords;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_patient_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PatientRecord patientRecord = mPatientRecords.get(position);
        holder.mNameTextView.setText(patientRecord.getName());
        holder.mRegisteredTimeTextView.setText(patientRecord.getRegisteredTime());
        holder.mRegisteredDateTextView.setText(patientRecord.getRegisteredDate());
    }

    @Override
    public int getItemCount() {
        return mPatientRecords.size();
    }
}

