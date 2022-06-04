package com.naca.database_termproject.p05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.databinding.SearchActivityBinding;
import com.naca.database_termproject.p06.MovieActivity;

import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity {

    private SearchActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.p05_activity_search);
        binding.setLifecycleOwner(this);

        EditText movieName = binding.toolbar05.p5Content.movieName;
        EditText openDate = binding.toolbar05.p5Content.openDate;
        TextView search_button = binding.toolbar05.p5Content.searchButton;

        LinkedList<Movie> movies = new LinkedList<>();
        movies.add(new Movie());

        RecyclerView movieList = binding.toolbar05.p5Content.recyMovie;
        movieList.setLayoutManager(new LinearLayoutManager(this));
        movieList.setHasFixedSize(true);

        MovieAdapter mAdapter = new MovieAdapter(movies);
        movieList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<Movie> tempList = new LinkedList<>();
                MovieAdapter adapter = new MovieAdapter(tempList);
                movieList.setAdapter(adapter);
            }
        });

    }
}
