package Registration;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.polyclinic2.Constant;
import com.example.polyclinic2.R;
import com.example.polyclinic2.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;

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
        int pola = 0;
        String id = mAuth.getCurrentUser().getUid();
        String fio = edFio.getText().toString();
        String[] words = fio.split(" ");
        if (words.length == 3) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Необходимо ввести все значения", Toast.LENGTH_SHORT).show();
        }
        String date = edDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date datePr = sdf.parse(date);
            pola += 1;
        } catch (ParseException e){
            Toast.makeText(this, "Введите дату рождения в формате дд.мм.гггг", Toast.LENGTH_SHORT).show();
        }
        String policy = edPolicy.getText().toString();
        if (policy.matches("\\d{16}")) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Ошибка в веденном полисе", Toast.LENGTH_SHORT).show();
        }
        String serial = edSerial.getText().toString();
        if (serial.matches("\\d{2} \\d{2} \\d{6}")) {
            pola += 1;
        }
        else {
            Toast.makeText(this, "Ошибка в веденных паспортных данных", Toast.LENGTH_SHORT).show();
        }
        String place = edPlace.getText().toString();

        if (pola == 4) {
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
}
