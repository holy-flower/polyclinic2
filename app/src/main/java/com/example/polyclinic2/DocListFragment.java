package com.example.polyclinic2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocListFragment extends Fragment implements DoctorAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> docList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference registeredDocsRef;
    String doctorId;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_doc_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        docList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(docList, getActivity(), this, bundle);
        recyclerView.setAdapter(doctorAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        registeredDocsRef = mDatabase.getReference("doctors");
        registeredDocsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot docSnapshot : snapshot.getChildren()) {
                    Doctor doctor = docSnapshot.getValue(Doctor.class);
                    docList.add(doctor);
                }
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(Doctor doctor) {
        String doctorId = doctor.getId();

        DoctorProfileFragment doctorProfileFragment = new DoctorProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constant.DOCTOR_ID, doctorId);
        doctorProfileFragment.setArguments(bundle);
    }
}