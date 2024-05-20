package com.example.polyclinic2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class DoctorProfileFragment extends Fragment {

    private TextView tvFioDoc, tvSpecialization, tvLicenseNumber, tvPhone;
    String docId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        tvFioDoc = view.findViewById(R.id.tvFioDoc);
        tvSpecialization = view.findViewById(R.id.tvSpecialization);
        tvLicenseNumber = view.findViewById(R.id.tvLicenseNumber);
        tvPhone = view.findViewById(R.id.tvPhone);

        Bundle bundle = getArguments();
        docId = bundle.getString(Constant.DOCTOR_ID);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference doctorRef = database.getReference("doctors").child(docId);

        doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                if (doctor != null){
                    tvFioDoc.setText(doctor.getFioDoc());
                    tvSpecialization.setText(doctor.getSpecialization());
                    tvLicenseNumber.setText(doctor.getLicense());
                    tvPhone.setText(doctor.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button bAppointment = view.findViewById(R.id.buttonPriem);
        bAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationFragment registrationFragment = new RegistrationFragment();

                Bundle b = new Bundle();
                b.putString("doctr", docId);
                registrationFragment.setArguments(b);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_doc_list, registrationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}