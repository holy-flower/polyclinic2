package com.example.polyclinic2;

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

import Lists.AppointListFragment;
import Lists.UserListFragment;
import Settings.SettingsDocFragment;

public class InterfaceDoc extends AppCompatActivity {
    private AppointListFragment appointListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_interface);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationDoc);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.homeDoc) {
                    Toast.makeText(InterfaceDoc.this, "home", Toast.LENGTH_SHORT).show();

                    UserListFragment userListFragment = new UserListFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_user_list, userListFragment);
                    ft.commit();
                    return true;
                } else if (menuItem.getItemId() == R.id.settingsDoc) {
                    Toast.makeText(InterfaceDoc.this, "Settings", Toast.LENGTH_SHORT).show();

                    SettingsDocFragment settingsDocFragment = new SettingsDocFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_user_list, settingsDocFragment);
                    ft.commit();

                    return true;
                }
                else if (menuItem.getItemId() == R.id.appointments) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentDoc = mAuth.getCurrentUser();

                    if (currentDoc != null) {
                        String doctorId = currentDoc.getUid();
                        AppointListFragment appointListFragment = new AppointListFragment();

                        Bundle args = new Bundle();
                        args.putString("123", doctorId);
                        appointListFragment.setArguments(args);
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_user_list, appointListFragment);
                        ft.commit();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void setAppointListFragment(AppointListFragment appointListFragment) {
        this.appointListFragment = appointListFragment;
    }
}
