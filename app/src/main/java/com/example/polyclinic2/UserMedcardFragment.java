package com.example.polyclinic2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMedcardFragment extends Fragment {

    private TextView FioUs, DateUs, PolicyUs, SerialUs, RegistrationUs, textNote;
    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_medcard, container, false);

        FioUs = view.findViewById(R.id.textViewFioUser);
        DateUs = view.findViewById(R.id.textViewDate);
        PolicyUs = view.findViewById(R.id.textViewPolicy);
        SerialUs = view.findViewById(R.id.textViewSerial);
        RegistrationUs = view.findViewById(R.id.textViewRegistration);
        textNote = view.findViewById(R.id.savedTextNode);

        Bundle bundle = getArguments();
        if(bundle != null) {
            userId = bundle.getString(Constant.USER_ID);
        }

        FirebaseDatabase databaseUser = FirebaseDatabase.getInstance();
        DatabaseReference userRef = databaseUser.getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    FioUs.setText(user.getFio());
                    DateUs.setText(user.getDate());
                    PolicyUs.setText(user.getPolicy());
                    SerialUs.setText(user.getSerial());
                    RegistrationUs.setText(user.getPlace());

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    String savedText = sharedPreferences.getString(userId, "");
                    textNote.setText(savedText);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}