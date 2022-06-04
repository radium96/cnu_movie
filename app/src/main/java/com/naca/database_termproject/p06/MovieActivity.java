package com.naca.database_termproject.p06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.R;
import com.naca.database_termproject.databinding.MovieActivityBinding;
import com.naca.database_termproject.p07.BookingActivity;

public class MovieActivity extends AppCompatActivity {

    private MovieActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p06_activity_movie);
        binding.setLifecycleOwner(this);

        TextView reservation = binding.toolbar06.p6Content.reservation;

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
    }
}
