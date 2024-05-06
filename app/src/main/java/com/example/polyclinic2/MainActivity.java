package com.example.polyclinic2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DoctorAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> docList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference registeredDocsRef;
    String doctorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.home) {
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();

                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    docList = new ArrayList<>();
                    doctorAdapter = new DoctorAdapter(docList, MainActivity.this, MainActivity.this);
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
                            Toast.makeText(MainActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                } else if (menuItem.getItemId() == R.id.settings) {
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(Doctor doctor) {
        String doctorId = doctor.getId();

        Intent intent = new Intent(MainActivity.this, DoctorProfileActivity.class);
        intent.putExtra(Constant.DOCTOR_ID, doctorId);
        intent.putExtra(Constant.DOCTOR, doctor);
        startActivity(intent);
    }
}
