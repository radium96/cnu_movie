package com.naca.database_termproject.p01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.Task;
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
                    try {
                        String query = "SELECT CID, PASSWORD FROM CUSTOMER WHERE CID = "
                                + input_id + " AND PASSWORD = '" + input_pw + "'";
                        Task task = new Task("login");
                        String result = task.execute(query).get();
                        if (result.equals(input_pw)){
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("id", input_id);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "회원번호 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("RESULT: ", result);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
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