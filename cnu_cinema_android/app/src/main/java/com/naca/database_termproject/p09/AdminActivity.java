package com.naca.database_termproject.p09;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.R;
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.databinding.AdminActivityBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class AdminActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private AdminActivityBinding binding;
    // 클릭한 질의문에 대해 결과값을 저장하는 String객체 LinkedList객체를 초기화한다.
    private final LinkedList<String> results = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p09_activity_admin);
        binding.setLifecycleOwner(this);

        // view에서 이용할 객체를 받아와 저장한다.
        TextView ticket_button = binding.toolbar09.p9Content.ticketButton;
        TextView join_button = binding.toolbar09.p9Content.joinButton;
        TextView group_button = binding.toolbar09.p9Content.groupButton;
        TextView window_button = binding.toolbar09.p9Content.windowButton;

        // 데이터베이스에서 초기 화면에 띄울 전체 예매 정보를 데이터베이스에서 불러온다.
        loadTicket();

        // 관람 기록 버튼을 클릭하면 데이터베이스에서 관람 기록을 불러와 view에 출력한다.
        ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTicket();
            }
        });

        // Join 버튼을 클릭하면 데이터베이스에서 Join관련 질의문에 대한 결과를 불러와 view에 출력한다.
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJoin();
            }
        });

        // Group 버튼을 클릭하면 데이터베이스에서 Grouping관련 질의문에 대한 결과를 불러와 view에 출력한다.
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGroup();
            }
        });

        // Window 버튼을 클릭하면 데이터베이스에서 Window함수 관련 질의문에 대한 결과를 불러와 view에 출력한다.
        window_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWindow();
            }
        });
    }

    // 데이터베이스에서 모든 예매 정보를 받아오고 view에 출력한다.
    void loadTicket() {
        // 불러온 예매정보를 저장할 Ticket객체의 LinkedList를 초기화한다.
        LinkedList<Ticket> tickets = new LinkedList<>();
        // 쿼리 질의문을 출력해주는 TextView를 보이지않도록 설정한다.
        binding.toolbar09.p9Content.query.setVisibility(View.GONE);
        try {
            // 모든 예매 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM TICKETING ORDER BY CID ASC";
            // 쿼리를 전달할 QueryTask를 생성하고 userticket으로 접속한다.
            QueryTask task = new QueryTask("userticket");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 모든 예매정보를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // tickets 리스트에 JSONObject에서 값을 받아와 Ticket객체로 추가한다.
                tickets.add(new Ticket(jo.getString("id"), jo.getString("title"), jo.getString("tname"), jo.getString("sdatetime"),
                        jo.getString("seat"), jo.getString("status"), jo.getString("rcdate"), jo.getString("cname")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // view에서 결과값을 출력하는 RecyclerView 객체를 받아온다
        RecyclerView resultList = binding.toolbar09.p9Content.recyAdmin;
        // RecyclerView의 LayoutManager를 LinearLayoutManager로 설정한다.
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setHasFixedSize(true);

        // RecyclerView의 값을 엮어줄 AdminAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        AdminAdapter mAdapter = new AdminAdapter(tickets);
        resultList.setAdapter(mAdapter);
    }

    // Join질의문에 대한 결과값을 받아오고 view에 출력한다.
    void loadJoin() {
        // results 리스트의 값을 모두 없애준다.
        results.clear();
        try {
            // Join질의문에 대한 쿼리를 작성한다.
            String query = "select C.name \"예매자 이름\", C.sex \"예매자 성\", C.cid \"예매자 id\", T.id \"티켓 id\", T.status \"티켓 상태\"\n" +
                    "from customer C inner join ticketing T\n" +
                    "on C.cid = t.cid\n" +
                    "order by C.cid";
            // 쿼리를 view에 출력한다.
            bind(query);
            // 쿼리를 전달할 QueryTask를 생성하고 join으로 접속한다.
            QueryTask task = new QueryTask("join");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 Join질의문에 대한 결과를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // results 리스트에 JSONObject를 String객체로 변환한 값을 추가한다.
                results.add(jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 결과값을 RecyclerView에 출력한다.
        setRecycler();
    }

    // Grouping질의문에 대한 결과값을 받아오고 view에 출력한다.
    void loadGroup() {
        // results 리스트의 값을 모두 없애준다.
        results.clear();
        try {
            // Grouping질의문에 대한 쿼리를 작성한다.
            String query = "select decode(grouping(sid), 1, '전체 예매 수', sid) as \"일정 id\", \n" +
                    "decode(grouping(name), 1, '전체 예매자', name) as \"예매자 이름\", count(*) \"예매 횟수\"\n" +
                    "from ticketing T, customer C\n" +
                    "where T.cid = C.cid\n" +
                    "group by rollup (sid, name)";
            // 쿼리를 view에 출력한다.
            bind(query);
            // 쿼리를 전달할 QueryTask를 생성하고 group으로 접속한다.
            QueryTask task = new QueryTask("group");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 Group질의문에 대한 결과를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // results 리스트에 JSONObject를 String객체로 변환한 값을 추가한다.
                results.add(jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 결과값을 RecyclerView에 출력한다.
        setRecycler();
    }

    // Window질의문에 대한 결과값을 받아오고 view에 출력한다.
    void loadWindow() {
        // results 리스트의 값을 모두 없애준다.
        results.clear();
        try {
            // Window질의문에 대한 쿼리를 작성한다.
            String query = "select cid \"고객 id\", count(*) \"예매 횟수\", rank() over (order by count(*) desc) \"예매 횟수 순위\"\n" +
                    "from ticketing\n" +
                    "group by cid";
            // 쿼리를 view에 출력한다.
            bind(query);
            // 쿼리를 전달할 QueryTask를 생성하고 window로 접속한다.
            QueryTask task = new QueryTask("window");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 Window질의문에 대한 결과를 저장한 JSONArray를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // results 리스트에 JSONObject를 String객체로 변환한 값을 추가한다.
                results.add(jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 결과값을 RecyclerView에 출력한다.
        setRecycler();
    }

    // 질의 결과를 RecyclerView에 출력한다.
    void setRecycler(){
        // 불러온 질의 결과를 출력할 RecyclerView를 view에서 받아온다.
        RecyclerView resultList = binding.toolbar09.p9Content.recyAdmin;
        // RecyclerView의 LayoutManager를 LinearLayoutManager로 설정한다.
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setHasFixedSize(true);

        // RecyclerView의 값을 엮어줄 AdminStringAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        AdminStringAdapter mAdapter = new AdminStringAdapter(results);
        resultList.setAdapter(mAdapter);
    }

    // 질의에 이용한 쿼리 정보를 view에 갱신한다.
    void bind(String query){
        // 쿼리가 출력될 TextView를 보이도록 설정한다.
        binding.toolbar09.p9Content.query.setVisibility(View.VISIBLE);
        binding.toolbar09.p9Content.setVariable(BR.query, query);
    }
}
