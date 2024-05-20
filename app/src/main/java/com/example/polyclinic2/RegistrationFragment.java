package com.example.polyclinic2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationFragment extends Fragment {
    String docId;
    TextView vtDocFio, vtSpecialization, dateVar, timeVar;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private SharedPreferences sharedPreferences;
    private String currentUserId;
    private String currentUserName;

    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Constant.POLICLINIC_PREFS, Context.MODE_PRIVATE);

        vtDocFio = view.findViewById(R.id.vtFioDoc);
        vtSpecialization = view.findViewById(R.id.vtSpecialization);
        dateVar = view.findViewById(R.id.dateVar);
        timeVar = view.findViewById(R.id.timeVar);
        Button bTimeVar = view.findViewById(R.id.buttonTime);
        Button bDataVar = view.findViewById(R.id.buttonDate);

        Bundle bundle = getArguments();
        docId = bundle.getString("doctr");


        currentUserId = sharedPreferences.getString(Constant.USER_ID_KEY, "");
        currentUserName = sharedPreferences.getString("username", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference("doctors").child(docId);

        docRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                if (doctor != null){
                    vtDocFio.setText(doctor.getFioDoc());
                    vtSpecialization.setText(doctor.getSpecialization());

                    bTimeVar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    timeVar.setText(hourOfDay + ":" + minute);
                                }
                            }, hour, minute, true);
                            timePickerDialog.show();
                        }
                    });

                    bDataVar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    dateVar.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                }
                            };
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    view.getContext(), dateSetListener, Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            );
                            datePickerDialog.show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button bReg = view.findViewById(R.id.bSaveReg);
        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentPatient = mAuth.getCurrentUser();

                String userId = currentPatient.getUid();
                DatabaseReference pat = database.getReference("users").child(userId);
                pat.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        userName = user.getFio();

                        AppointListFragment appointListFragment = new AppointListFragment();

                        String appointmentTime = timeVar.getText().toString();
                        String appointmentDate = dateVar.getText().toString();
                        DatabaseReference appointmentsRef = database.getReference("appointments");

                        PatientRecord newPatient = new PatientRecord(docId, userName, appointmentDate, appointmentTime);
                        appointmentsRef.child(userId).child(appointmentDate + " " + appointmentTime).setValue(newPatient);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_doc_list, appointListFragment);
                        ft.commit();

                        Toast.makeText(getActivity(), "Регистрация прошла", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
}