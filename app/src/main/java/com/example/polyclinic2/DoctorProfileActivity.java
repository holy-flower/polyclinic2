package com.example.polyclinic2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileActivity extends AppCompatActivity {
    private TextView tvFioDoc, tvSpecialization, tvLicenseNumber, tvPhone;
    String docId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docprofile_activity);

        tvFioDoc = findViewById(R.id.tvFioDoc);
        tvSpecialization = findViewById(R.id.tvSpecialization);
        tvLicenseNumber = findViewById(R.id.tvLicenseNumber);
        tvPhone = findViewById(R.id.tvPhone);

        docId = getIntent().getStringExtra(Constant.DOCTOR_ID);

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


    }
}
