package com.example.polyclinic2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {
    private TextView tvFio, tvDate, tvPolicy, tvSerial, tvRegistration;
    String userId;
    EditText editTextNote;
    Button bSavedText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        tvFio = view.findViewById(R.id.tvFioUser);
        tvDate = view.findViewById(R.id.tvDate);
        tvPolicy = view.findViewById(R.id.tvPolicy);
        tvSerial = view.findViewById(R.id.tvSerial);
        tvRegistration = view.findViewById(R.id.tvRegistration);
        editTextNote = view.findViewById(R.id.editTextNote);
        bSavedText = view.findViewById(R.id.bSavedText);

        Bundle bundle = getArguments();
        userId = bundle.getString(Constant.USER_ID);

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
                    bSavedText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String noteText = editTextNote.getText().toString();

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString(userId, noteText);
                            myEdit.commit();

                            Toast.makeText(getActivity(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}