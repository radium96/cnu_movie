package com.naca.database_termproject.p03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.User;
import com.naca.database_termproject.databinding.HomeActivityBinding;
import com.naca.database_termproject.p04.UserActivity;
import com.naca.database_termproject.p05.SearchActivity;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private HomeActivityBinding binding;

    // 로그인한 사용자의 id값과 사용자의 정보를 저장할 User객체를 선언한다.
    // 정적 변수로 선언하여 다른 Activity에서도 접근할 수 있도록 한다.
    public static String id;
    public static User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p03_activity_home);
        binding.setLifecycleOwner(this);

        // 이전 Activity에서 id값을 받아와 id에 저장한다.
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        id = b.getString("id");

        // 데이터베이스에서 유저의 정보를 받아오는 loadUser함수를 실행한다.
        loadUser();

        // view에서 이용할 객체를 받아와 저장한다.
        TextView inform = binding.toolbar03.p3Content.information;
        TextView search = binding.toolbar03.p3Content.search;

        // 회원 정보 버튼을 클릭하면 유저 Activity로 이동한다.
        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        // 영화 검색 버튼을 클릭하면 영화 검색 Activity로 이동한다.
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    // 데이터베이스에서 유저의 정보를 받아온다.
    void loadUser(){
        try {
            // 사용자 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM CUSTOMER WHERE CID = " + id;
            // 쿼리를 전달할 QueryTask를 생성하고 user로 접속한다.
            QueryTask task = new QueryTask("user");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 사용자의 정보를 저장한 JSONObject를 전달받는다.
            String result = task.execute(query).get();
            // JSONObject에 result를 입력하여 초기화한다.
            JSONObject ja = new JSONObject(result);

            // JSONObject에서 값을 받아와 user에 초기화하여 저장한다.
            user = new User(ja.getString("name"), ja.getString("email"),
                    ja.getString("birth"), ja.getString("sex"));
            Log.d("RESULT: ", result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
