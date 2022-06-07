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
import com.naca.database_termproject.Task;
import com.naca.database_termproject.data.User;
import com.naca.database_termproject.databinding.HomeActivityBinding;
import com.naca.database_termproject.p04.UserActivity;
import com.naca.database_termproject.p05.SearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private HomeActivityBinding binding;
    public static String id;
    public static User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p03_activity_home);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        id = b.getString("id");
        load();

        TextView inform = binding.toolbar03.p3Content.information;
        TextView search = binding.toolbar03.p3Content.search;

        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    void load(){
        try {
            String query = "SELECT * FROM CUSTOMER WHERE CID = " + id;
            Task task = new Task("user");
            String result = task.execute(query).get();
            JSONObject ja = new JSONObject(result);

            user = new User(ja.getString("name"), ja.getString("email"),
                    ja.getString("birth"), ja.getString("sex"));
            Log.d("RESULT: ", result);
            Log.d("USERL ", user.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
