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

public class InterfaceDoc extends AppCompatActivity implements UserAdapter.OnItemClickUserListener {
    private RecyclerView recyclerViewUser;
    private UserAdapter userAdapter;
    private List<User> userList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference registeredUsersRef;

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

                    recyclerViewUser = findViewById(R.id.recyclerViewDoc);
                    recyclerViewUser.setLayoutManager(new LinearLayoutManager(InterfaceDoc.this));

                    userList = new ArrayList<>();
                    userAdapter = new UserAdapter(userList, InterfaceDoc.this);
                    recyclerViewUser.setAdapter(userAdapter);

                    mDatabase = FirebaseDatabase.getInstance();
                    registeredUsersRef = mDatabase.getReference("users");
                    registeredUsersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null) {
                                userList.clear();
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    if (userSnapshot != null){
                                        User user = userSnapshot.getValue(User.class);
                                        User userObject = new User(user.getFio());
                                        userList.add(userObject);
                                    }
                                }
                                userAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(InterfaceDoc.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                } else if (menuItem.getItemId() == R.id.settingsDoc) {
                    Toast.makeText(InterfaceDoc.this, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemUserClick(User user) {
        Intent intent = new Intent(InterfaceDoc.this, DoctorProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
