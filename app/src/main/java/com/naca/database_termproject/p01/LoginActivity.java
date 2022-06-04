package com.naca.database_termproject.p01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.HomeActivityBinding;
import com.naca.database_termproject.databinding.LoginActivityBinding;
import com.naca.database_termproject.p02.SignupActivity;
import com.naca.database_termproject.p03.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p01_activity_login);
        binding.setLifecycleOwner(this);

        EditText id = binding.toolbar01.p1Content.cid;
        EditText pw = binding.toolbar01.p1Content.password;
        TextView login = binding.toolbar01.p1Content.loginButton;
        TextView signup = binding.toolbar01.p1Content.signupButton;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_id = id.getText().toString();
                String input_pw = pw.getText().toString();

                if(input_id.equals("") || input_pw.equals("")){
                    Toast.makeText(LoginActivity.this, "회원번호 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
