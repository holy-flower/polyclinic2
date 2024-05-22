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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpDocActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDataBase;
    private EditText edFioDoc, edSpecialization, edLicense, edPhone;
    private String DOC_KEY = "doctors";
    String emailDoc, passDoc;
    ActionBar actionBar;
    FirebaseDatabase lDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docsignup_layout);
        init();

        Intent intent = getIntent();
        emailDoc = intent.getStringExtra(Constant.DOC_EMAIL);
        passDoc = intent.getStringExtra(Constant.DOC_PASS);
    }

    private void init(){
        edFioDoc = findViewById(R.id.edFioDoc);
        edLicense = findViewById(R.id.edLicenseNumber);
        edSpecialization = findViewById(R.id.edSpecialization);
        edPhone = findViewById(R.id.edPhone);

        mDataBase = FirebaseDatabase.getInstance().getReference(DOC_KEY);
    }

    public void onClickAddDoc(View view){
        int pola = 0;
        String id = mAuth.getCurrentUser().getUid();
        String fioDoc = edFioDoc.getText().toString();
        String[] words = fioDoc.split(" ");
        if (words.length == 3) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Необходимо ввести все значения", Toast.LENGTH_SHORT).show();
        }
        String license = edLicense.getText().toString();
        String pattern = "^Л\\d{3}-\\d{5}-\\d{2}/\\d{8}$";
        if (license.matches(pattern)) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Номер лицензии не правильно введен", Toast.LENGTH_SHORT).show();
        }
        String specialization = edSpecialization.getText().toString();
        String phone = edPhone.getText().toString();
        if (phone.matches("^\\+7\\s\\d{3}\\s\\d{3}-\\d{2}-\\d{2}$")) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Номер телефона не правильно введен", Toast.LENGTH_SHORT).show();
        }

        if (pola == 3) {
            if (!TextUtils.isEmpty(emailDoc) && !TextUtils.isEmpty(passDoc) &&
                    !TextUtils.isEmpty(fioDoc) && !TextUtils.isEmpty(license) &&
                    !TextUtils.isEmpty(specialization) && !TextUtils.isEmpty(phone)) {
                mAuth.signInWithEmailAndPassword(emailDoc, passDoc).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Doctor newDoc = new Doctor(id, fioDoc, license, specialization, phone);

                        mDataBase.child(id).setValue(newDoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(SignUpDocActivity.this, LogInDocActivity.class);
                                startActivity(intent);
                                Toast.makeText(SignUpDocActivity.this, "Данные пользователя успешно сохранены", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpDocActivity.this, "Ошибка при сохранении данных" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(SignUpDocActivity.this, "saved", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                Toast.makeText(this, "Please enter all margins", Toast.LENGTH_LONG).show();
            }
        }
    }
}
