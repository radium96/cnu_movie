package com.naca.database_termproject.p04;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.databinding.UserActivityBinding;

import java.util.LinkedList;

public class UserActivity extends AppCompatActivity {

    private UserActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p04_activity_user);
        binding.setLifecycleOwner(this);

        LinkedList<Ticket> tickets = new LinkedList<>();
        tickets.add(new Ticket());

        RecyclerView ticketing = binding.toolbar04.p4Content.recyBooking;
        ticketing.setLayoutManager(new LinearLayoutManager(this));
        ticketing.setHasFixedSize(true);

        TicketAdapter mAdapter = new TicketAdapter(tickets);
        ticketing.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }
}
