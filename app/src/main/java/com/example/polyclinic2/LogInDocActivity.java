package com.example.polyclinic2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInDocActivity extends AppCompatActivity {

    private EditText edLoginDoc, edPasswordDoc;
    private FirebaseAuth mAuth;
    private Button bStartDoc, bSignUpDoc, bLogInDoc, bSignOutDoc;
    private TextView tvDocName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doclogin_layout);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cDoc = mAuth.getCurrentUser();
        if (cDoc != null){
            showSigned();
            String docName = "Вы вошли как: " + cDoc.getEmail();
            tvDocName.setText(docName);

            Toast.makeText(this, "doc not null", Toast.LENGTH_LONG).show();
        }
        else {
            notSigned();
            Toast.makeText(this, "doc null", Toast.LENGTH_LONG).show();
        }
    }

    private void init(){
        tvDocName = findViewById(R.id.tvDocEmail);
        bStartDoc = findViewById(R.id.buttonStartingDoc);
        bSignUpDoc = findViewById(R.id.buttonSignDoc);
        bLogInDoc = findViewById(R.id.buttonLogDoc);
        edLoginDoc = findViewById(R.id.edLoginDoc);
        edPasswordDoc = findViewById(R.id.edPasswordDoc);
        bSignOutDoc = findViewById(R.id.buttonSignOutDoc);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickDocSignUp(View view) {
        String edEmailDoc = edLoginDoc.getText().toString();
        String edPassDoc = edPasswordDoc.getText().toString();
        if (!TextUtils.isEmpty(edEmailDoc) && !TextUtils.isEmpty(edPassDoc)) {
            mAuth.createUserWithEmailAndPassword(edEmailDoc, edPassDoc)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LogInDocActivity.this, SignUpDocActivity.class);
                                intent.putExtra(Constant.DOC_EMAIL, edEmailDoc);
                                intent.putExtra(Constant.DOC_PASS, edPassDoc);
                                startActivity(intent);
                            }
                            else {
                                notSigned();
                                Toast.makeText(getApplicationContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "Введите почту и пароль", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDocLogIn(View view){
        if (!TextUtils.isEmpty(edLoginDoc.getText().toString()) && !TextUtils.isEmpty(edPasswordDoc.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLoginDoc.getText().toString(), edPasswordDoc.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        showSigned();
                        Toast.makeText(getApplicationContext(), "doc sign ip successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        notSigned();
                        Toast.makeText(getApplicationContext(), "doc sign ip failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void onClickSignOutDoc(View view){
        FirebaseAuth.getInstance().signOut();
        notSigned();
    }

    private void showSigned(){
        FirebaseUser doctor = mAuth.getCurrentUser();
        String docName = "Вы вошли как: " + doctor.getEmail();
        tvDocName.setText(docName);
        bStartDoc.setVisibility(View.VISIBLE);
        tvDocName.setVisibility(View.VISIBLE);
        bSignOutDoc.setVisibility(View.VISIBLE);
        edLoginDoc.setVisibility(View.GONE);
        edPasswordDoc.setVisibility(View.GONE);
        bSignUpDoc.setVisibility(View.GONE);
        bLogInDoc.setVisibility(View.GONE);
    }

    private void notSigned(){
        bStartDoc.setVisibility(View.GONE);
        tvDocName.setVisibility(View.GONE);
        bSignOutDoc.setVisibility(View.GONE);
        edLoginDoc.setVisibility(View.VISIBLE);
        edPasswordDoc.setVisibility(View.VISIBLE);
        bSignUpDoc.setVisibility(View.VISIBLE);
        bLogInDoc.setVisibility(View.VISIBLE);
    }

    public void onClickStartDoc(View view){
        Intent intent = new Intent(LogInDocActivity.this, InterfaceDoc.class);
        startActivity(intent);
    }
}
