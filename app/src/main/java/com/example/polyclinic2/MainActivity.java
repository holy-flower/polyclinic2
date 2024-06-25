package com.example.polyclinic2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import Lists.AppointListFragment;
import Lists.DocListFragment;
import Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private List<PatientRecord> patientRecords = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.POLICLINIC_PREFS, MODE_PRIVATE);

        AppointListFragment appointListFragment = new AppointListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientRecords", (Serializable) patientRecords);
        appointListFragment.setArguments(bundle);

        InterfaceDoc interfaceDoc = new InterfaceDoc();
        interfaceDoc.setAppointListFragment(appointListFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.home) {
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();

                    DocListFragment docListFragment = new DocListFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_doc_list, docListFragment);
                    ft.commit();
                    return true;
                } else if (menuItem.getItemId() == R.id.settings) {
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_doc_list, settingsFragment);
                    ft.commit();

                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.medical_card) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        UserMedcardFragment userMedcardFragment = new UserMedcardFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.USER_ID, userId);
                        userMedcardFragment.setArguments(bundle);

                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_doc_list, userMedcardFragment);
                        ft.commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}