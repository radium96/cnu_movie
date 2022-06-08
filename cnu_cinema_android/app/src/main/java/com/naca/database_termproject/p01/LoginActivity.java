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
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.databinding.LoginActivityBinding;
import com.naca.database_termproject.p02.SignupActivity;
import com.naca.database_termproject.p03.HomeActivity;
import com.naca.database_termproject.p09.AdminActivity;

public class LoginActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private LoginActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p01_activity_login);
        binding.setLifecycleOwner(this);

        // view에서 이용할 객체를 받아와 저장한다.
        EditText id = binding.toolbar01.p1Content.cid;
        EditText pw = binding.toolbar01.p1Content.password;
        TextView login = binding.toolbar01.p1Content.loginButton;
        TextView signup = binding.toolbar01.p1Content.signupButton;

        // 로그인 버튼을 클릭시 기재된 onClickListener를 실행한다.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인에 이용할 id와 pw의 값을 받아와 input_id, input_pw로 저장한다.
                String input_id = id.getText().toString();
                String input_pw = pw.getText().toString();

                // 만약 두 값 중 하나라도 비어있다면 Toast를 통해 오류 메세지를 출력한다.
                if(input_id.equals("") || input_pw.equals("")){
                    Toast.makeText(LoginActivity.this, "회원번호 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // 로그인에 이용한 값이 유효한 값인지 확인하는 쿼리를 작성한다.
                        String query = "SELECT CID, PASSWORD FROM CUSTOMER WHERE CID = "
                                + input_id + " AND PASSWORD = '" + input_pw + "'";
                        // 쿼리를 전달할 QueryTask를 생성하고 login으로 접속한다.
                        QueryTask task = new QueryTask("login");
                        // task에 query를 값으로 넣고 결과값을 result에 저장한다.
                        // result는 입력한 비밀번호를 전달받는다.
                        String result = task.execute(query).get();
                        // 결과값과 input_pw를 비교하여 같은 값인지 확인한다.
                        if (result.equals(input_pw)){
                            // 만약 id가 99999라면 관리자 계정이므로 관리자 Activity로 이동한다.
                            if (input_id.equals("99999")){
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            // 관리자 계정이 아니라면 홈 Activity로 이동한다.
                            else {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                // 로그인한 사용자의 id값을 다음 Activity에 전달한다.
                                intent.putExtra("id", input_id);
                                // 다음 Activity를 실행하고 해당 Activity는 종료한다.
                                startActivity(intent);
                                finish();
                            }
                        }
                        // 만약 두 값이 같지 않다면 Toast를 통해 오류 메세지를 출력한다.
                        else {
                            Toast.makeText(LoginActivity.this, "회원번호 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("RESULT: ", result);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        // 회원가입 Activity로 이동한다.
        // 초기 구상에서는 회원가입을 구현하려했으나 개인적 한계로 구현하지 않았다.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
