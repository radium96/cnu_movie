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
import com.naca.database_termproject.task.EmailTask;
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ScheduleActivityBinding;
import com.naca.database_termproject.p03.HomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class ScheduleActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private ScheduleActivityBinding binding;
    // 이전 Activity에서 선택된 스케쥴의 정보를 저장하는 Schedule객체이다.
    private Schedule schedule;
    // 선택 불가능한 좌석의 값을 저장할 Integer형 LinkedList객체를 초기화한다.
    private LinkedList<Integer> disable = new LinkedList<>();
    // 선택한 좌석의 값을 저장할 Integer형 LinkedList객체를 초기화한다.
    private LinkedList<Integer> select = new LinkedList<>();
    // RecyclerView에서 좌석의 번호를 출력할때 이용할 Integer형 LinkedList객체를 초기화한다.
    private LinkedList<Integer> allSeat = new LinkedList<>();
    // 좌석수를 선택할 Spinner의 값을 저장해둘 String배열 객체이다.
    private String[] items;
    // 선택할 좌석의 개수를 저장할 int형 배열 객체를 초기화하고 값을 1로 저장한다.
    // 무명메소드에 이용할 값이므로 배열로 저장한다.
    private final int[] seatNum = {1};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p08_activity_schedule);
        binding.setLifecycleOwner(this);

        // 이전 Activity에서 스케쥴 정보를 받아와 schedule에 저장한다.
        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("select");

        // 스케쥴의 정보를 view에 출력한다.
        bind(schedule);

        // 데이터베이스에서 상영관의 전체 좌석, 이미 예약이 되어있는 좌석을 불러온다.
        // 불러온 값을 기준으로 최대 예약 관객수를 지정해준다.
        loadSeats();
        // 몇 좌석을 예약할지 값을 받는 Spinner를 설정한다.
        setSpinner();
        // 받아온 좌석 정보를 기준으로 RecyclerView를 갱신한다.
        setRecycler();

        // 예매하기 버튼 객체를 view에서 받아온다.
        TextView reserve = binding.toolbar08.p8Content.reservation;

        // 예매하기 버튼을 클릭했을 때 기재된 onClickListener를 실행한다.
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 좌석수와 선택한 좌석의 개수를 확인하여 두 값이 같으면 예매를 진행한다.
                if (select.size() == seatNum[0]){
                    insertTicket();
                    finish();
                }
                // 두 값이 다르다면 Toast로 에러 메세지를 출력한다.
                else {
                    Toast.makeText(ScheduleActivity.this, "예약할 좌석을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 데이터베이스에서 해당 스케쥴의 좌석 정보를 받아온다.
    void loadSeats(){
        try{
            // 이미 예약되어있는 좌석의 값을 받아오는 쿼리를 작성한다.
            String query = "SELECT SEATS FROM TICKETING WHERE SID = " + schedule.getSid() + " AND STATUS = 'R'";
            // 쿼리를 전달할 QueryTask를 생성하고 seats로 접속한다.
            QueryTask task = new QueryTask("seats");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 스케쥴에 대해 이미 예약된 좌석의 값을 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // disable 리스트에 JSONObject에서 값을 받아와 int형 값을 추가한다.
                disable.add(Integer.parseInt(jo.getString("seats")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 예약 가능한 좌석의 개수를 계산하여 available에 저장한다.
        int available = Integer.parseInt(schedule.getSeats()) - Integer.parseInt(schedule.getDisable());
        // 만약 available의 값이 10보다 작다면 items의 값을 1부터 available까지로 설정한다.
        if(available < 10){
            items = new String[available];
            for(int i = 0;i<available;i++){
                items[i] = Integer.toString(i+1);
            }
        }
        // 그렇지 않다면 items의 값을 1부터 10까지로 설정한다.
        else {
            items = new String[10];
            for(int i = 0;i<10;i++){
                items[i] = Integer.toString(i+1);
            }
        }
    }

    // 예약할 좌석수를 선택할 Spinner를 세팅한다.
    void setSpinner(){
        // Spinner객체를 view에서 받아온다.
        Spinner spinner = binding.toolbar08.p8Content.scheduleSeat;
        // Spinner의 값을 설정해줄 ArrayAdapter를 선언하고 값을 items로 초기화한다.
        // Spinner가 보이는 형태를 android.R.layout.simple_spinner_item로 설정한다.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        // ArrayAdapter에서 Spinner를 DropDown했을 때의 형태를 android.R.layout.simple_spinner_dropdown_item로 설정한다.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Spinner에 ArrayAdapter를 등록하고 디폴트값을 0으로 설정한다.
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        // Spinner에서 item을 선택했을 때 기재된 OnItemSelectedListener를 실행한다.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // seatNum[0]의 값을 선택한 item의 값을 int형으로 형변환한 값으로 저장한다.
                seatNum[0] = Integer.parseInt(items[position]);
                // 좌석을 표현한 RecyclerView를 초기화하고 선택한 좌석을 저장한 select 리스트를 비운다.
                setRecycler();
                select.clear();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 좌석 정보를 출력할 RecyclerView를 설정한다.
    void setRecycler(){
        // 설정할 RecyclerView 객체를 view에서 받아온다.
        RecyclerView seatList = binding.toolbar08.p8Content.recySeat;
        // RecyclerView의 LayoutManager를 GridLayoutManager로 설정한다.
        // Grid의 개수를 5로 설정한다.
        seatList.setLayoutManager(new GridLayoutManager(this, 5));
        seatList.setHasFixedSize(true);

        // 스케쥴에서 좌석의 개수를 받아와 allSeat에 저장한다.
        allSeat = new LinkedList<>();
        for(int i = 0;i<Integer.parseInt(schedule.getSeats());i++){
            allSeat.add(i + 1);
        }
        // RecyclerView의 값을 엮어줄 SeatAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        SeatAdapter mAdapter = new SeatAdapter(allSeat, disable);
        seatList.setAdapter(mAdapter);

        // RecyclerView의 item을 클릭했을 때 기재된 onClickListener를 실행한다.
        mAdapter.setOnItemClickListener(new SeatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 선택한 view가 선택된 상태라면 해당 분기문을 실행한다.
                if(v.isHovered()){
                    // for 반복문을 통해 select 리스트에서 position + 1의 값을 찾아 제거한다.
                    for(int i = 0;i<select.size();i++){
                        if(position + 1 == select.get(i)){
                            select.remove(i);
                            break;
                        }
                    }
                    // 선택한 view의 상태를 선택하지않은 상태로 변경한다.
                    v.setHovered(false);
                }
                // 선택한 view가 선택된 상태가 아니라면 해당 분기문을 실행한다.
                else {
                    // 만약 select의 크기가 seatNum[0]의 값과 같다면 모든 좌석을 선택했다는 에러메세지를 Toast로 출력한다.
                    if (select.size() == seatNum[0]){
                        Toast.makeText(ScheduleActivity.this, "모든 좌석을 선택했습니다.", Toast.LENGTH_SHORT).show();
                    }
                    // select의 크기가 seatNum[0]보다 작으면 select에 position + 1을 추가하고 해당 view를 선택한 상태로 변환한다.
                    else {
                        select.add(position + 1);
                        Collections.sort(select);
                        v.setHovered(true);
                    }
                }
            }
        });
    }

    // 최종 예매 정보를 데이터베이스에 전송한다.
    void insertTicket(){
        // 현재 시각을 저장하여 Date객체에 저장한다.
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        // Date객체에 저장된 시간을 일정한 포맷으로 출력되도록하는 SimpleDateFormat을 선언하고 포맷을 설정한다.
        @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // for 반복문을 통해 선택한 좌석의 개수만큼 쿼리 전송을 진행한다.
            for (int i = 0;i<select.size();i++){
                // TICKETING 테이블에 예약정보를 추가하는 쿼리를 작성한다.
                String query = "INSERT INTO TICKETING (RC_DATE, SEATS, STATUS, CID, SID) VALUES (TO_DATE('"
                        + form.format(d) + "', 'YYYY-MM-DD HH24:MI'), " + select.get(i) + ", 'R', " + HomeActivity.id + ", "
                        + schedule.getSid() + ")";
                // 쿼리를 전달할 QueryTask를 생성하고 update로 접속한다.
                QueryTask task = new QueryTask("update");
                // task에 query를 값으로 넣고 결과값을 result에 저장한다.
                // result는 갱신 결과값을 전달받는다.
                String result = task.execute(query).get();
                Log.d("RESULT: ", result);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 모든 티켓에 대해 데이터베이스에 입력이 완료되면 사용자의 이메일로 메일을 전송한다.
        sendEmail();
        // 모든 과정을 마치면 Toast로 완료 메세지를 출력한다.
        Toast.makeText(ScheduleActivity.this, "예매가 완료되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 예매 정보를 사용자의 이메일로 전송해준다.
    void sendEmail(){
        try {
            // 이메일을 보낼 EamilTask를 생성하고 ticketing으로 접속한다.
            EmailTask task = new EmailTask("ticketing");
            // 사용자의 이메일을 받아 to에 저장한다.
            String to = HomeActivity.user.getEmail();
            // 보낼 메일의 제목을 title에 저장한다.
            String title = "예약 완료";
            // 보낼 메일의 내용을 설계할 StringBuilder를 선언하고 내용을 저장한다.
            StringBuilder contentBuilder =
                    new StringBuilder("영화 제목: " + schedule.getTitle() + "\n" +
                            "상영 일자: " + schedule.getSdate() + "\n" +
                            "상영 시간: " + schedule.getStime() + "\n" +
                            "예약 인원: " + seatNum[0] + "명\n" +
                            "예약 좌석: ");
            for (int i = 0;i<select.size();i++){
                if(i + 1 == select.size()){
                    contentBuilder.append(select.get(i)).append("석");
                } else {
                    contentBuilder.append(select.get(i)).append("석, ");
                }
            }
            // 완성된 StringBuilder의 값을 content의 저장한다.
            String content = contentBuilder.toString();
            // task에 to, title, content를 값으로 넣고 결과값을 result에 저장한다.
            // result는 이메일 전송 성공, 실패를 받는다.
            String result = task.execute(to, title, content).get();
            Log.d("RESULT: ", result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 스케쥴 정보를 view에 갱신한다.
    void bind(Schedule schedule){
        binding.toolbar08.p8Content.setVariable(BR.reservation, schedule);
    }
}
