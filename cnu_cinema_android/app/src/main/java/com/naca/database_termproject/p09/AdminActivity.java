package com.naca.database_termproject.p09;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.AdminActivityBinding;
import com.naca.database_termproject.databinding.ScheduleActivityBinding;

public class AdminActivity extends AppCompatActivity {

    private AdminActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p09_activity_admin);
        binding.setLifecycleOwner(this);

    }
}
