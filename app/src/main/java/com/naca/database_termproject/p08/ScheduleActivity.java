package com.naca.database_termproject.p08;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.ScheduleActivityBinding;

public class ScheduleActivity extends AppCompatActivity {

    private ScheduleActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p08_activity_schedule);
        binding.setLifecycleOwner(this);

        TextView reserve = binding.toolbar08.p8Content.reservation;

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
