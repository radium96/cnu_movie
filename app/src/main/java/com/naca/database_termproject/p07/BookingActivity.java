package com.naca.database_termproject.p07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ReserveActivityBinding;
import com.naca.database_termproject.p08.ScheduleActivity;

import java.util.LinkedList;

public class BookingActivity extends AppCompatActivity {

    private ReserveActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p07_activity_reserve);
        binding.setLifecycleOwner(this);

        LinkedList<Schedule> schedules = new LinkedList<>();
        schedules.add(new Schedule());

        RecyclerView scheduleList = binding.toolbar07.p7Content.recySchedule;
        scheduleList.setLayoutManager(new LinearLayoutManager(this));
        scheduleList.setHasFixedSize(true);

        ScheduleAdapter mAdapter = new ScheduleAdapter(schedules);
        scheduleList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(BookingActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

    }
}
