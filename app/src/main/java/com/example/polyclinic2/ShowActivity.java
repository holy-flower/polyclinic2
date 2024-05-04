package com.example.polyclinic2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity  extends AppCompatActivity {
    private TextView tvName, tvPAss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slow_layout);
        init();
        getIntentMain();
    }

    private void init(){
        tvName = findViewById(R.id.tvName);
        tvPAss = findViewById(R.id.tvPass);
    }

    private void getIntentMain(){
        Intent intent = getIntent();
        if (intent != null){
            tvName.setText(intent.getStringExtra(Constant.USER_NAME));
            tvPAss.setText(intent.getStringExtra(Constant.USER_PASS));
        }
    }
}
