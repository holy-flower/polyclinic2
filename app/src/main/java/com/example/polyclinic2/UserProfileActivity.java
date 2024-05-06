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

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvFio, tvDate, tvPolicy, tvSerial, tvRegistration;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        tvFio = findViewById(R.id.tvFioUser);
        tvDate = findViewById(R.id.tvDate);
        tvPolicy = findViewById(R.id.tvPolicy);
        tvSerial = findViewById(R.id.tvSerial);
        tvRegistration = findViewById(R.id.tvRegistration);

        userId = getIntent().getStringExtra(Constant.USER_ID);

        FirebaseDatabase databaseUser = FirebaseDatabase.getInstance();
        DatabaseReference userRef = databaseUser.getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    tvFio.setText(user.getFio());
                    tvDate.setText(user.getDate());
                    tvPolicy.setText(user.getPolicy());
                    tvSerial.setText(user.getSerial());
                    tvRegistration.setText(user.getPlace());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
