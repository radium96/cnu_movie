package com.naca.database_termproject.p07;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.Task;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ReserveActivityBinding;
import com.naca.database_termproject.p08.ScheduleActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

public class BookingActivity extends AppCompatActivity {

    private ReserveActivityBinding binding;
    private LinkedList<Schedule> schedules = new LinkedList<>();
    private Movie movie;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p07_activity_reserve);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("select");
        c.setTimeZone(TimeZone.getTimeZone("Pacific/Honolulu"));
        loadSchedule();

        RecyclerView scheduleList = binding.toolbar07.p7Content.recySchedule;
        scheduleList.setLayoutManager(new LinearLayoutManager(this));
        scheduleList.setHasFixedSize(true);

        ScheduleAdapter mAdapter = new ScheduleAdapter(schedules, movie.getTitle());
        scheduleList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(BookingActivity.this, ScheduleActivity.class);
                intent.putExtra("select", schedules.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ONRESTART", "1");
        schedules.clear();
        loadSchedule();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ONRESUME", "1");
    }

    void loadSchedule(){
        try{
            c.add(Calendar.HOUR, 9);
            Date d = c.getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Log.d("DATE", form.format(d));
            String query = "SELECT * FROM MOVIE_SCHEDULE WHERE MID = '" + movie.getMid() +
                    "' AND SDATETIME > TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD HH24:MI')";
            Task task = new Task("schedule");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            Log.d("JALENGTH: ", Integer.toString(ja.length()));
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                schedules.add(new Schedule(jo.getString("sdatetime"), jo.getString("tname"), jo.getString("seats"),
                        jo.getString("disable"), movie.getMid(), jo.getString("sid"), movie.getTitle()));
                Log.d("SCHEDULE", schedules.getLast().toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
