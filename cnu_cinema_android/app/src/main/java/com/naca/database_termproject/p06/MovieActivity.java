package com.naca.database_termproject.p06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.R;
import com.naca.database_termproject.Task;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.databinding.MovieActivityBinding;
import com.naca.database_termproject.p07.BookingActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieActivity extends AppCompatActivity {

    private MovieActivityBinding binding;
    private Movie movie;
    private String[] cnt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p06_activity_movie);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("select");
        loadCount();
        bind(movie);
        TextView reservation = binding.toolbar06.p6Content.reservation;

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, BookingActivity.class);
                intent.putExtra("select", movie);
                startActivity(intent);
            }
        });
    }

    void loadCount(){
        try{
            String query = "SELECT COUNT(CASE WHEN T.STATUS = 'W' THEN 1 END) cnt1, COUNT(CASE WHEN T.STATUS = 'R' THEN 1 END) cnt2 " +
                    "FROM TICKETING T, MOVIE_SCHEDULE S WHERE T.SID = S.SID AND S.MID = '" + movie.getMid() + "'";
            Task task = new Task("moviedetail");
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            cnt = result.split(" ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void bind(Movie movie){
        binding.toolbar06.p6Content.setVariable(BR.selected, movie);
        binding.toolbar06.p6Content.setVariable(BR.cnt1, cnt[0]);
        binding.toolbar06.p6Content.setVariable(BR.cnt2, cnt[1]);
    }
}
