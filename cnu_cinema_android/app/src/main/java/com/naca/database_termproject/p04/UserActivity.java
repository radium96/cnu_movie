package com.naca.database_termproject.p04;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.R;
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.data.User;
import com.naca.database_termproject.databinding.UserActivityBinding;
import com.naca.database_termproject.p03.HomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class UserActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private UserActivityBinding binding;
    // 사용자의 예매내역을 저장할 Ticket의 LinkedList를 초기화한다.
    private final LinkedList<Ticket> tickets = new LinkedList<>();
    // 검색에 이용할 날짜들과 상태를 선언한다.
    private String sdate;
    private String edate;
    private String st;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_user);
        binding.setLifecycleOwner(this);

        // 사용자 정보를 view에 연결하여 출력한다.
        bind(HomeActivity.user);

        // view에서 이용할 객체를 받아와 저장한다.
        TextView button = binding.toolbar04.p4Content.searchButton;
        EditText status = binding.toolbar04.p4Content.status;
        EditText start_date = binding.toolbar04.p4Content.startDate;
        EditText end_date = binding.toolbar04.p4Content.endDate;

        // 검색 버튼 클릭시 기재된 onClickListener를 실행한다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색에 이용할 검색 기준 시작일, 종료일, 상태를 받아와 sdate, edate, st에 저장한다.
                sdate = start_date.getText().toString();
                edate = end_date.getText().toString();
                st = status.getText().toString();

                // 만약 세 값 중 하나라도 비어있다면 Toast를 통해 오류 메세지를 출력한다.
                if(sdate.equals("") || edate.equals("") || st.equals("")){
                    Toast.makeText(UserActivity.this, "모든 칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 예매정보를 받아올 loadTicket함수에 sdate, edate, st값을 넣어 실행한다.
                    loadTicket(sdate, edate, st);
                    // 받아온 예매정보를 RecyclerView에 갱신한다.
                    setRecycler();
                }
            }
        });
    }

    // 데이터베이스에서 입력받은 값을 기준으로 예매정보를 받아온다.
    void loadTicket(String sdate, String edate, String status){
        // tickets 리스트의 모든 값을 제거한다.
        tickets.clear();
        try {
            // 기준일과 상태를 기준으로 예매 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM TICKETING WHERE CID = " + HomeActivity.id +
                    " AND RC_DATE >= TO_DATE('" + sdate + "', 'YYYY/MM/DD')" +
                    " AND RC_DATE <= TO_DATE('" + edate + "', 'YYYY/MM/DD')" +
                    " AND STATUS = '" + status + "' ORDER BY RC_DATE DESC";
            // 쿼리를 전달할 QueryTask를 생성하고 userticket으로 접속한다.
            QueryTask task = new QueryTask("userticket");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 기준에 따른 사용자의 예매정보를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // tickets 리스트에 JSONObject에서 값을 받아와 Ticket객체로 추가한다.
                tickets.add(new Ticket(jo.getString("id"), jo.getString("title"), jo.getString("tname"),
                        jo.getString("sdatetime"), jo.getString("seat"), jo.getString("status"), jo.getString("rcdate")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 예매 정보를 출력할 RecyclerView를 설정한다.
    void setRecycler(){
        // 설정할 RecyclerView 객체를 view에서 받아온다.
        RecyclerView ticketing = binding.toolbar04.p4Content.recyBooking;
        // RecyclerView의 LayoutManager를 LinearLayoutManager로 설정한다.
        ticketing.setLayoutManager(new LinearLayoutManager(this));
        ticketing.setHasFixedSize(true);

        // RecyclerView의 값을 엮어줄 TicketAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        TicketAdapter mAdapter = new TicketAdapter(tickets);
        ticketing.setAdapter(mAdapter);

        // RecyclerView의 item을 클릭했을 때 기재된 onClickListener를 실행한다.
        mAdapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 클릭된 위치의 예매 정보를 받아온다.
                Ticket t = tickets.get(position);
                // 클릭한 객체의 상태가 예매 완료 상태라면 예매를 취소하고 상태를 데이터베이스에 전달하여 갱신한다..
                if(t.getStatus().equals("예매 완료")){
                    // 현재 시각을 저장하여 Date객체에 저장한다.
                    Calendar c = Calendar.getInstance();
                    Date d = c.getTime();
                    // Date객체에 저장된 시간을 일정한 포맷으로 출력되도록하는 SimpleDateFormat을 선언하고 포맷을 설정한다.
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
                    // 예매 정보의 상태를 예매 취소로 변경한다.
                    t.setStatus("예매 취소");
                    try{
                        // 해당 예매 정보의 상태를 갱신하는 쿼리를 작성한다.
                        String query = "UPDATE TICKETING SET STATUS = 'C' WHERE ID = " + t.getId();
                        // 쿼리를 전달할 QueryTask를 생성하고 update로 접속한다.
                        QueryTask task = new QueryTask("update");
                        // task에 query를 값으로 넣고 결과값을 result에 저장한다.
                        // result는 반환 상태값을 전달받는다.
                        String result = task.execute(query).get();
                        Log.d("RESULT: ", result);

                        // 해당 예매 정보의 최종 갱신 시간을 갱신하는 쿼리를 작성한다.
                        query = "UPDATE TICKETING SET RC_DATE = TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD') WHERE ID = " + t.getId();
                        // 쿼리를 전달할 QueryTask를 생성하고 update로 접속한다.
                        task = new QueryTask("update");
                        // task에 query를 값으로 넣고 결과값을 result에 저장한다.
                        // result는 반환 상태값을 전달받는다.
                        result = task.execute(query).get();
                        Log.d("RESULT: ", result);

                        // 예매정보를 다시 갱신하여 RecyclerView에 출력한다.
                        loadTicket(sdate, edate, st);
                        setRecycler();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    // 모든 과정을 마치고 예매 취소가 완료되었다는 Toast를 출력한다.
                    Toast.makeText(UserActivity.this, "예매 취소가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 사용자 정보를 view에 갱신한다.
    void bind(User user) {
            binding.toolbar04.p4Content.setVariable(BR.user, user);
    }
}
