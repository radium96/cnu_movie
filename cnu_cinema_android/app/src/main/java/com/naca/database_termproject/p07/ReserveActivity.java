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
import com.naca.database_termproject.task.QueryTask;
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

public class ReserveActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private ReserveActivityBinding binding;
    // 영화의 상영 스케쥴를 저장할 Schedule의 LinkedList를 초기화한다.
    private LinkedList<Schedule> schedules = new LinkedList<>();
    // 상영할 영화의 정보를 저장할 Movie객체이다.
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p07_activity_reserve);
        binding.setLifecycleOwner(this);

        // 이전 Activity에서 영화 정보를 받아와 movie에 저장한다.
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("select");

        // 데이터베이스에서 해당 영화에 대한 예약 가능한 스케쥴을 불러온다.
        loadSchedule();

        // 스케쥴 정보를 출력할 RecyclerView를 설정한다.
        RecyclerView scheduleList = binding.toolbar07.p7Content.recySchedule;
        // RecyclerView의 LayoutManager를 LinearLayoutManager로 설정한다.
        scheduleList.setLayoutManager(new LinearLayoutManager(this));
        scheduleList.setHasFixedSize(true);

        // RecyclerView의 값을 엮어줄 ScheduleAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        ScheduleAdapter mAdapter = new ScheduleAdapter(schedules);
        scheduleList.setAdapter(mAdapter);

        // RecyclerView의 item을 클릭했을 때 기재된 onClickListener를 실행한다.
        mAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ReserveActivity.this, ScheduleActivity.class);
                // 클릭한 위치의 스케쥴 정보를 받아와 스케쥴 Activity로 넘겨준다.
                intent.putExtra("select", schedules.get(position));
                // 스케쥴 Activity를 실행한다.
                startActivity(intent);
            }
        });
    }

    // 데이터베이스에서 영화에 대한 예약가능한 스케쥴 정보를 받아온다.
    void loadSchedule(){
        try{
            // 현재 시각을 저장하여 Date객체에 저장한다.
            Calendar c = Calendar.getInstance();
            Date d = c.getTime();
            // Date객체에 저장된 시간을 일정한 포맷으로 출력되도록하는 SimpleDateFormat을 선언하고 포맷을 설정한다.
            @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // 영화의 예약 가능한 스케쥴 절보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM MOVIE_SCHEDULE WHERE MID = '" + movie.getMid() +
                    "' AND SDATETIME > TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD HH24:MI')" +
                    " ORDER BY SDATETIME";
            // 쿼리를 전달할 QueryTask를 생성하고 schedule로 접속한다.
            QueryTask task = new QueryTask("schedule");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 예약 가능한 스케쥴의 정보를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // movies 리스트에 JSONObject에서 값을 받아와 Movie객체로 추가한다.
                schedules.add(new Schedule(jo.getString("sdatetime"), jo.getString("tname"), jo.getString("seats"),
                        jo.getString("disable"), movie.getMid(), jo.getString("sid"), movie.getTitle()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
