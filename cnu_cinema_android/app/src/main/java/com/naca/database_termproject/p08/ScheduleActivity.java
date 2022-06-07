package com.naca.database_termproject.p08;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.Task;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ScheduleActivityBinding;
import com.naca.database_termproject.p01.LoginActivity;
import com.naca.database_termproject.p03.HomeActivity;
import com.naca.database_termproject.p04.UserActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class ScheduleActivity extends AppCompatActivity {

    private ScheduleActivityBinding binding;
    private Schedule schedule;
    private LinkedList<Integer> disable = new LinkedList<>();
    private LinkedList<Integer> select = new LinkedList<>();
    private LinkedList<Integer> allSeat = new LinkedList<>();

    private SeatAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p08_activity_schedule);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("select");
        bind(schedule);
        loadSeats();

        int available = Integer.parseInt(schedule.getSeats()) - Integer.parseInt(schedule.getDisable());
        String[] items;
        if(available < 10){
            items = new String[available];
            for(int i = 0;i<available;i++){
                items[i] = Integer.toString(i+1);
            }
        } else {
            items = new String[10];
            for(int i = 0;i<10;i++){
                items[i] = Integer.toString(i+1);
            }
        }

        Spinner spinner = binding.toolbar08.p8Content.scheduleSeat;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        final int[] seatNum = {1};

        RecyclerView seatList = binding.toolbar08.p8Content.recySeat;
        seatList.setLayoutManager(new GridLayoutManager(this, 5));
        seatList.setHasFixedSize(true);

        allSeat = new LinkedList<>();
        for(int i = 0;i<Integer.parseInt(schedule.getSeats());i++){
            allSeat.add(i + 1);
        }
        mAdapter = new SeatAdapter(allSeat, disable);
        seatList.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seatNum[0] = Integer.parseInt(items[position]);
                mAdapter = new SeatAdapter(allSeat, disable);
                seatList.setAdapter(mAdapter);
                select.clear();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAdapter.setOnItemClickListener(new SeatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(v.isHovered()){
                    for(int i = 0;i<select.size();i++){
                        if(position + 1 == select.get(i)){
                            select.remove(i);
                            break;
                        }
                    }
                    v.setHovered(false);
                } else {
                    if (select.size() == seatNum[0]){
                        Toast.makeText(ScheduleActivity.this, "모든 좌석을 선택했습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        select.add(position + 1);
                        v.setHovered(true);
                    }
                }
            }
        });

        TextView reserve = binding.toolbar08.p8Content.reservation;

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.size() == seatNum[0]){
                    insertTicket();
                } else {
                    Toast.makeText(ScheduleActivity.this, "예약할 좌석을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        });
    }

    void bind(Schedule schedule){
        binding.toolbar08.p8Content.setVariable(BR.reservation, schedule);
    }

    void insertTicket(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 9);
        Date d = c.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 0;i<select.size();i++){
                Task task = new Task("update");
                String query = "INSERT INTO TICKETING (RC_DATE, SEATS, STATUS, CID, SID) VALUES (TO_DATE('"
                        + form.format(d) + "', 'YYYY-MM-DD HH24:MI'), " + select.get(i) + ", 'R', " + HomeActivity.id + ", "
                        + schedule.getSid() + ")";
                String result = task.execute(query).get();
                Log.d("RESULT: ", result);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(ScheduleActivity.this, "예매가 완료되었습니다.", Toast.LENGTH_SHORT).show();
    }

    void loadSeats(){
        try{
            String query = "SELECT SEATS FROM TICKETING WHERE SID = " + schedule.getSid() + " AND STATUS = 'R'";
            Task task = new Task("seats");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            Log.d("JALENGTH", Integer.toString(ja.length()));
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                disable.add(Integer.parseInt(jo.getString("seats")));
            }
            Log.d("DIS", String.valueOf(disable.size()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
