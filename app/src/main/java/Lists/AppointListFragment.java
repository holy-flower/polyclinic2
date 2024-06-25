package Lists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.polyclinic2.PatientRecord;
import Adapters.PatientRecordAdapter;
import com.example.polyclinic2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointListFragment extends Fragment {
    private RecyclerView rvPatientRecords;
    private List<PatientRecord> patientRecords;
    private PatientRecordAdapter patientRecordAdapter;
    private String docIntId;

    public AppointListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appoint_list, container, false);


        rvPatientRecords = view.findViewById(R.id.rvAppointments);
        rvPatientRecords.setLayoutManager(new LinearLayoutManager(getActivity()));

        patientRecords = new ArrayList<>();
        patientRecordAdapter = new PatientRecordAdapter((ArrayList<PatientRecord>) patientRecords);
        rvPatientRecords.setAdapter(patientRecordAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            docIntId = bundle.getString("123");
        }

        FirebaseDatabase databasePatient = FirebaseDatabase.getInstance();
        DatabaseReference patientRef = databasePatient.getReference("appointments");

        Log.d("docID", docIntId);

        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientRecords.clear();
                if(snapshot.exists()) {
                    Log.d("patientRecord","val="+snapshot.getChildrenCount());
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for(DataSnapshot date: dataSnapshot.getChildren()) {
                            PatientRecord patientRecord = date.getValue(PatientRecord.class);
                            Log.d("patientRecord", "tutttttt");
                            Log.d("patientRecord", patientRecord.toString());
                            if (patientRecord.getDoctorId().equals(docIntId)) {
                                patientRecords.add(patientRecord);
                            }
                        }
                    }
                    patientRecordAdapter.notifyDataSetChanged();
                    Log.d("patientRecord", "patientRecords: " + patientRecords);
                } else {
                    Log.d("patientRecord","!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Listener was cancelled: " + error.getCode());
            }
        });


        return view;
    }
}
