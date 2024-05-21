package com.example.polyclinic2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class AppointListFragment extends Fragment {
    private RecyclerView rvPatientRecords;
    private List<PatientRecord> patientRecords;
    private PatientRecordAdapter patientRecordAdapter;
    private String docIntId;

    public AppointListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appoint_list, container, false);

        rvPatientRecords = view.findViewById(R.id.rvAppointments);
        rvPatientRecords.setLayoutManager(new LinearLayoutManager(getActivity()));

        patientRecords = new ArrayList<>();
        patientRecordAdapter = new PatientRecordAdapter((ArrayList<PatientRecord>) patientRecords);
        rvPatientRecords.setAdapter(patientRecordAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            docIntId = bundle.getString("123");
        }

        FirebaseDatabase databasePatient = FirebaseDatabase.getInstance();
        DatabaseReference patientRef = databasePatient.getReference("appointments");

        Log.d("docID", docIntId);

        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientRecords.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PatientRecord patientRecord = dataSnapshot.getValue(PatientRecord.class);
                    Log.d("patientRecord", "tutttttt");
                    if (patientRecord.getDoctorId().equals(docIntId)) {
                        patientRecords.add(patientRecord);
                    }
                    //patientRecords.add(patientRecord);
                }
                patientRecordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Listener was cancelled: " + error.getCode());
            }
        });

        return view;
    }
}
