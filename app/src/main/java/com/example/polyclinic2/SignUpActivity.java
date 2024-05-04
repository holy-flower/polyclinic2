package com.example.polyclinic2;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDataBase;
    private EditText edFio, edDate, edPolicy, edSerial, edPlace;
    private String USER_KEY = "users";
    String email, password;
    ActionBar actionBar;

    FirebaseDatabase lDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        init();

        Intent intent = getIntent();
        email = intent.getStringExtra(Constant.USER_EMAIL);
        password = intent.getStringExtra(Constant.USER_PASSWORD);
    }

    private void init(){
        edFio = findViewById(R.id.edFio);
        edDate = findViewById(R.id.edDate);
        edPolicy = findViewById(R.id.edPolicy);
        edSerial = findViewById(R.id.edSerial);
        edPlace = findViewById(R.id.edRegistration);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickAddUser(View view){
        String id = mAuth.getCurrentUser().getUid(); // Используйте UID текущего пользователя в качестве идентификатора
        String fio = edFio.getText().toString();
        String date = edDate.getText().toString();
        String policy = edPolicy.getText().toString();
        String serial = edSerial.getText().toString();
        String place = edPlace.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(fio) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(policy) &&
                !TextUtils.isEmpty(serial) && !TextUtils.isEmpty(place)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    User newUser = new User(id, fio, date, policy, serial, place);

                    mDataBase.child(id).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUpActivity.this, "Данные пользователя успешно сохранены", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Ошибка при сохранении данных" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(SignUpActivity.this, "saved", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Please enter all margins", Toast.LENGTH_LONG).show();
        }
    }
}
