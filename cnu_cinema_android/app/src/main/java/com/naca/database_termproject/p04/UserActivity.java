package com.naca.database_termproject.p04;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.LinkedList;

public class UserActivity extends AppCompatActivity {

    private UserActivityBinding binding;
    private JSONObject jo = new JSONObject();
    private final LinkedList<Ticket> tickets = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_user);
        binding.setLifecycleOwner(this);
        bind(HomeActivity.user);
        loadTicket();
        setRecycler();
    }

    void loadTicket(){
        try {
            String query = "SELECT * FROM TICKETING WHERE CID = "
                    + HomeActivity.id;
            Task task = new Task("userticket");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            JSONArray ja = new JSONArray(result);
            Log.d("JALENGTH", Integer.toString(ja.length()));
            for (int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                tickets.add(new Ticket(jo.getString("id"), jo.getString("title"), jo.getString("tname"),
                        jo.getString("sdatetime"), jo.getString("seat"), jo.getString("status")));
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

        TicketAdapter mAdapter = new TicketAdapter(tickets);
        ticketing.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Ticket t = tickets.get(position);
                if(t.getStatus().equals("예매 완료")){
                    t.setStatus("예매 취소");
                    try{
                        String query = "UPDATE TICKETING SET STATUS = 'C' WHERE ID = " + t.getId();
                        Task task = new Task("update");
                        String result = task.execute(query).get();
                        Log.d("RESULT: ", result);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(UserActivity.this, "예매 취소가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void bind(User user) {
            binding.toolbar04.p4Content.setVariable(BR.user, user);
    }
}
