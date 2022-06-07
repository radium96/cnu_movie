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
import com.naca.database_termproject.Task;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.data.User;
import com.naca.database_termproject.databinding.UserActivityBinding;
import com.naca.database_termproject.p01.LoginActivity;
import com.naca.database_termproject.p03.HomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class UserActivity extends AppCompatActivity {

    private UserActivityBinding binding;
    private JSONObject jo = new JSONObject();
    private final LinkedList<Ticket> tickets = new LinkedList<>();
    private TicketAdapter mAdapter;
    private String sdate;
    private String edate;
    private String st;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_user);
        binding.setLifecycleOwner(this);
        bind(HomeActivity.user);

        TextView button = binding.toolbar04.p4Content.searchButton;
        EditText status = binding.toolbar04.p4Content.status;
        EditText start_date = binding.toolbar04.p4Content.startDate;
        EditText end_date = binding.toolbar04.p4Content.endDate;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdate = start_date.getText().toString();
                edate = end_date.getText().toString();
                st = status.getText().toString();

                if(sdate.equals("") || edate.equals("") || st.equals("")){
                    Toast.makeText(UserActivity.this, "모든 칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    loadTicket(sdate, edate, st);
                    setRecycler();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    void loadTicket(String sdate, String edate, String status){
        tickets.clear();
        try {
            String query = "SELECT * FROM TICKETING WHERE CID = " + HomeActivity.id +
                    " AND RC_DATE >= TO_DATE('" + sdate + "', 'YYYY/MM/DD')" +
                    " AND RC_DATE <= TO_DATE('" + edate + "', 'YYYY/MM/DD')" +
                    " AND STATUS = '" + status + "' ORDER BY RC_DATE DESC";
            Task task = new Task("userticket");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            Log.d("JALENGTH", Integer.toString(ja.length()));
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                tickets.add(new Ticket(jo.getString("id"), jo.getString("title"), jo.getString("tname"),
                        jo.getString("sdatetime"), jo.getString("seat"), jo.getString("status"), jo.getString("rcdate")));
            }

            Log.d("TICKET: ", Integer.toString(tickets.size()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void setRecycler(){
        RecyclerView ticketing = binding.toolbar04.p4Content.recyBooking;
        ticketing.setLayoutManager(new LinearLayoutManager(this));
        ticketing.setHasFixedSize(true);

        mAdapter = new TicketAdapter(tickets);
        ticketing.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Ticket t = tickets.get(position);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.HOUR, 9);
                Date d = c.getTime();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
                if(t.getStatus().equals("예매 완료")){
                    t.setStatus("예매 취소");
                    try{
                        String query = "UPDATE TICKETING SET STATUS = 'C' WHERE ID = " + t.getId();
                        Task task = new Task("update");
                        String result = task.execute(query).get();
                        Log.d("RESULT: ", result);

                        query = "UPDATE TICKETING SET RC_DATE = TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD') WHERE ID = " + t.getId();
                        task = new Task("update");
                        result = task.execute(query).get();
                        Log.d("RESULT: ", result);
                        loadTicket(sdate, edate, st);
                        setRecycler();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    Toast.makeText(UserActivity.this, "예매 취소가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void bind(User user) {
            binding.toolbar04.p4Content.setVariable(BR.user, user);
    }
}
