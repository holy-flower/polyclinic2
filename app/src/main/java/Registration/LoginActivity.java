package Registration;

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

import com.example.polyclinic2.Constant;
import com.example.polyclinic2.MainActivity;
import com.example.polyclinic2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edLogin, edPassword;
    private FirebaseAuth mAuth;
    private Button bStart, bSignUp, bLogIn, bSignOut, bDoc;
    private TextView tvUserName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null){
            showSigned();
            String userName = "Вы вошли как: " + cUser.getEmail();
            tvUserName.setText(userName);

            Toast.makeText(this, "user not null", Toast.LENGTH_LONG).show();
        }
        else {
            notSigned();
            Toast.makeText(this, "user null", Toast.LENGTH_LONG).show();
        }
    }

    private void init(){
        tvUserName = findViewById(R.id.tvUserEmail);
        bStart = findViewById(R.id.buttonStarting);
        bSignUp = findViewById(R.id.buttonSign);
        bLogIn = findViewById(R.id.buttonLog);
        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPassword);
        bSignOut = findViewById(R.id.buttonSignOut);
        bDoc = findViewById(R.id.bottonDoc);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSignUp(View view) {
        String edEmail = edLogin.getText().toString();
        String edPasswordStr = edPassword.getText().toString();
        if (!TextUtils.isEmpty(edEmail) && !TextUtils.isEmpty(edPasswordStr)) {
            mAuth.createUserWithEmailAndPassword(edEmail, edPasswordStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                intent.putExtra(Constant.USER_EMAIL, edEmail);
                                intent.putExtra(Constant.USER_PASSWORD, edPasswordStr);
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

    public void onClickLogIn(View view){
        if (!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        showSigned();
                        Toast.makeText(getApplicationContext(), "user sign ip successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        notSigned();
                        Toast.makeText(getApplicationContext(), "user sign ip failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void onClickSignOut(View view){
        FirebaseAuth.getInstance().signOut();
        notSigned();
    }

    private void showSigned(){
        FirebaseUser user = mAuth.getCurrentUser();
        String userName = "Вы вошли как: " + user.getEmail();
        tvUserName.setText(userName);
        bStart.setVisibility(View.VISIBLE);
        tvUserName.setVisibility(View.VISIBLE);
        bSignOut.setVisibility(View.VISIBLE);
        edLogin.setVisibility(View.GONE);
        edPassword.setVisibility(View.GONE);
        bSignUp.setVisibility(View.GONE);
        bLogIn.setVisibility(View.GONE);
        bDoc.setVisibility(View.GONE);
    }

    private void notSigned(){
        bStart.setVisibility(View.GONE);
        tvUserName.setVisibility(View.GONE);
        bSignOut.setVisibility(View.GONE);
        edLogin.setVisibility(View.VISIBLE);
        edPassword.setVisibility(View.VISIBLE);
        bSignUp.setVisibility(View.VISIBLE);
        bLogIn.setVisibility(View.VISIBLE);
        bDoc.setVisibility(View.VISIBLE);
    }

    public void onClickStart(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickDoc(View view){
        Intent intent = new Intent(LoginActivity.this, LogInDocActivity.class);
        startActivity(intent);
    }
}
