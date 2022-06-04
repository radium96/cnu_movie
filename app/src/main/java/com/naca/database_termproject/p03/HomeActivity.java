package com.naca.database_termproject.p03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.HomeActivityBinding;
import com.naca.database_termproject.p04.UserActivity;
import com.naca.database_termproject.p05.SearchActivity;

public class HomeActivity extends AppCompatActivity {

    private HomeActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p03_activity_home);
        binding.setLifecycleOwner(this);

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
}
