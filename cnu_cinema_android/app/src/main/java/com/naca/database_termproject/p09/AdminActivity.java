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

    private AdminActivityBinding binding;
    private final LinkedList<Ticket> tickets = new LinkedList<>();
    private final LinkedList<String> results = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p09_activity_admin);
        binding.setLifecycleOwner(this);

        TextView ticket_button = binding.toolbar09.p9Content.ticketButton;
        TextView join_button = binding.toolbar09.p9Content.joinButton;
        TextView group_button = binding.toolbar09.p9Content.groupButton;
        TextView window_button = binding.toolbar09.p9Content.windowButton;

        loadTicket();

        ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTicket();
            }
        });
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJoin();
            }
        });
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGroup();
            }
        });
        window_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWindow();
            }
        });
    }

    void loadTicket() {
        binding.toolbar09.p9Content.query.setVisibility(View.GONE);
        tickets.clear();
        try {
            String query = "SELECT * FROM TICKETING ORDER BY CID ASC";
            QueryTask task = new QueryTask("userticket");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                tickets.add(new Ticket(jo.getString("id"), jo.getString("title"), jo.getString("tname"), jo.getString("sdatetime"),
                        jo.getString("seat"), jo.getString("status"), jo.getString("rcdate"), jo.getString("cname")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        RecyclerView resultList = binding.toolbar09.p9Content.recyAdmin;
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setHasFixedSize(true);

        AdminAdapter mAdapter = new AdminAdapter(tickets);
        resultList.setAdapter(mAdapter);
    }

    void loadJoin() {
        results.clear();
        try {
            String query = "select C.name \"예약자 이름\", C.sex \"예약자 성\", C.cid \"예약자 id\", T.id \"티켓 id\", T.status \"티켓 상태\"\n" +
                    "from customer C inner join ticketing T\n" +
                    "on C.cid = t.cid\n" +
                    "order by C.cid";
            bind(query);
            QueryTask task = new QueryTask("join");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                results.add(jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        setRecycler();
    }

    void loadGroup() {
        results.clear();
        try {
            String query = "select decode(grouping(sid), 1, '전체 예약 수', sid) as \"일정 id\", \n" +
                    "decode(grouping(name), 1, '해당 일정 예약 수', name) as \"예약자 이름\", count(*) \"예약 횟수\"\n" +
                    "from ticketing T, customer C\n" +
                    "where T.cid = C.cid\n" +
                    "group by rollup (sid, name)";
            bind(query);
            QueryTask task = new QueryTask("group");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                results.add(jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        setRecycler();
    }

    void loadWindow() {
        results.clear();
        try {
            String query = "select cid \"고객 id\", count(*) \"예약 횟수\", rank() over (order by count(*) desc) \"예약 횟수 순위\"\n" +
                    "from ticketing\n" +
                    "group by cid";
            bind(query);
            QueryTask task = new QueryTask("window");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                results.add(jo.toString());
                Log.d("JO", jo.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        setRecycler();
    }

    void setRecycler(){
        RecyclerView resultList = binding.toolbar09.p9Content.recyAdmin;
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setHasFixedSize(true);

        AdminStringAdapter mAdapter = new AdminStringAdapter(results);
        resultList.setAdapter(mAdapter);
    }

    void bind(String query){
        binding.toolbar09.p9Content.query.setVisibility(View.VISIBLE);
        binding.toolbar09.p9Content.setVariable(BR.query, query);
    }
}
