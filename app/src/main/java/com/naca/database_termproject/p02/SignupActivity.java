package com.naca.database_termproject.p02;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.SignupActivityBinding;
public class SignupActivity extends AppCompatActivity {

    private SignupActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p02_activity_signup);
        binding.setLifecycleOwner(this);

        EditText pw = binding.toolbar02.p2Content.password;
        EditText email = binding.toolbar02.p2Content.email;
        EditText birthDate = binding.toolbar02.p2Content.birthDate;
        EditText sex = binding.toolbar02.p2Content.sex;
        TextView signup = binding.toolbar02.p2Content.signupButton;

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_pw = pw.getText().toString();
                String input_email = email.getText().toString();
                String input_date = birthDate.getText().toString();
                String input_sex = sex.getText().toString();

                if(input_pw.isEmpty() || input_email.isEmpty() || input_date.isEmpty() || input_sex.isEmpty()){
                    Toast.makeText(SignupActivity.this, "모든 칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    finish();
                }
            }
        });
    }
}
