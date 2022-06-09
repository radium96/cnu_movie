package com.naca.database_termproject.p05;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.R;
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.databinding.SearchActivityBinding;
import com.naca.database_termproject.p06.MovieActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private SearchActivityBinding binding;
    // 영화의 정보를 저장할 Movie의 LinkedList를 초기화한다.
    private LinkedList<Movie> movies = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p05_activity_search);
        binding.setLifecycleOwner(this);

        // view에서 이용할 객체를 받아와 저장한다.
        EditText movieName = binding.toolbar05.p5Content.movieName;
        EditText openDate = binding.toolbar05.p5Content.openDate;
        TextView search_button = binding.toolbar05.p5Content.searchButton;

        // 데이터베이스에서 영화의 정보를 받아오는 loadMovie함수를 실행한다.
        loadMovie();
        // 받아온 영화정보를 기준으로 RecyclerView를 갱신한다.
        setRecycler();

        // 검색 버튼 클릭시 기재된 onClickListener를 실행한다.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // movies 리스트의 모든 값을 제거한다.
                movies.clear();
                // 검색에 이용할 영화 제목, 관람 예정 일자를 받아와 search_title, search_openDate에 저장한다.
                String search_title = movieName.getText().toString();
                String search_openDate = openDate.getText().toString();
                // 만약 search_title과 search_openDate의 값이 모두 비어있다면 모든 영화의 정보를 받아온다.
                if (search_title.equals("") && search_openDate.equals("")){
                    loadMovie();
                }
                // 만약 search_openDate만 비어있다면 영화 제목을 기준으로 검색을 진행한다.
                else if (search_openDate.equals("")) {
                    searchMovieTitle(search_title);
                }
                // 만약 search_title만 비어있다면 관람 예정 일자를 기준으로 검색을 진행한다.
                else if (search_title.equals("")) {
                    searchMovieDate(search_openDate);
                }
                // 만약 두 값 모두 채워져있다면 두 값 모두를 기준으로 검색을 진행한다.
                else {
                    searchMovieTitleDate(search_title, search_openDate);
                }
                // 받아온 영화 정보를 RecyclerView에 갱신한다.
                setRecycler();
            }
        });
    }

    // 영화 정보를 출력할 RecyclerView를 설정한다.
    void setRecycler(){
        // 설정할 RecyclerView 객체를 view에서 받아온다.
        RecyclerView movieList = binding.toolbar05.p5Content.recyMovie;
        // RecyclerView의 LayoutManager를 LinearLayoutManager로 설정한다.
        movieList.setLayoutManager(new LinearLayoutManager(this));
        movieList.setHasFixedSize(true);

        // RecyclerView의 값을 엮어줄 MovieAdapter를 선언하고 RecyclerView의 Adapter로 설정한다.
        MovieAdapter mAdapter = new MovieAdapter(movies);
        movieList.setAdapter(mAdapter);

        // RecyclerView의 item을 클릭했을 때 기재된 onClickListener를 실행한다.
        mAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
                // 클릭한 위치의 영화 정보를 받아와 검색 Activity로 넘겨준다.
                intent.putExtra("select", movies.get(position));
                // 검색 Activity를 실행한다.
                startActivity(intent);
            }
        });
    }

    // 데이터베이스에서 모든 영화의 정보를 받아온다.
    void loadMovie(){
        try{
            // 현재 시각을 저장하여 Date객체에 저장한다.
            Calendar c = Calendar.getInstance();
            Date d = c.getTime();
            // Date객체에 저장된 시간을 일정한 포맷으로 출력되도록하는 SimpleDateFormat을 선언하고 포맷을 설정한다.
            @SuppressLint("SimpleDateFormat") SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // 모든 영화의 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM MOVIE WHERE OPEN_DAY > TO_DATE('" + form.format(d) + "', 'YYYY-MM-DD HH24:MI') - 10 ORDER BY OPEN_DAY";
            // 쿼리를 전달할 QueryTask를 생성하고 movie로 접속한다.
            QueryTask task = new QueryTask("movie");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 영화의의 정보를 저장한 JSONObject를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // movies 리스트에 JSONObject에서 값을 받아와 Movie객체로 추가한다.
                movies.add(new Movie(jo.getString("mid"), jo.getString("title"), jo.getString("openDay"),
                        jo.getString("director"), jo.getString("rating"), jo.getString("length")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 데이터베이스에서 입력한 제목을 가진 영화의 정보를 받아온다.
    void searchMovieTitle(String title){
        try{
            // 입력한 제목을 가진 영화의 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM MOVIE WHERE TITLE = '" + title + "'";
            // 쿼리를 전달할 QueryTask를 생성하고 movie로 접속한다.
            QueryTask task = new QueryTask("movie");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 영화의의 정보를 저장한 JSONObject를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // movies 리스트에 JSONObject에서 값을 받아와 Movie객체로 추가한다.
                movies.add(new Movie(jo.getString("mid"), jo.getString("title"), jo.getString("openDay"),
                        jo.getString("director"), jo.getString("rating"), jo.getString("length")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 데이터베이스에서 관람 예정 날짜를 기준으로 관람이 가능한 영화의 정보를 받아온다.
    void searchMovieDate(String date){
        try{
            // 관람 예정 날짜를 기준으로 10일 전 날짜부터 기준일까지 개봉된 영화의 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM MOVIE WHERE OPEN_DAY >= TO_DATE('" + date + "', 'YYYY/MM/DD') - 10 AND OPEN_DAY <= TO_DATE('" +
                    date + "', 'YYYY/MM/DD') ORDER BY OPEN_DAY";
            // 쿼리를 전달할 QueryTask를 생성하고 movie로 접속한다.
            QueryTask task = new QueryTask("movie");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 영화의의 정보를 저장한 JSONObject를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // movies 리스트에 JSONObject에서 값을 받아와 Movie객체로 추가한다.
                movies.add(new Movie(jo.getString("mid"), jo.getString("title"), jo.getString("openDay"),
                        jo.getString("director"), jo.getString("rating"), jo.getString("length")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 데이터베이스에서 영화 제목과 관람 예정 날짜를 기준으로 관람이 가능한 영화의 정보를 받아온다.
    void searchMovieTitleDate(String title, String date){
        try{
            // 입력받은 영화 제목과 일치하고 관람 예정 날짜를 기준으로 10일 전 날짜부터 기준일까지 개봉된 영화의 정보를 받아오는 쿼리를 작성한다.
            String query = "SELECT * FROM MOVIE WHERE OPEN_DAY >= TO_DATE('" + date + "', 'YYYY/MM/DD') - 10 AND OPEN_DAY <= TO_DATE('" +
                    date + "', 'YYYY/MM/DD') AND TITLE = '" + title + "'";
            // 쿼리를 전달할 QueryTask를 생성하고 movie로 접속한다.
            QueryTask task = new QueryTask("movie");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 영화의의 정보를 저장한 JSONObject를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // JSONArray에 result를 입력하여 초기화한다.
            JSONArray ja = new JSONArray(result);
            // JSONArray의 길이를 기준으로 for반복문을 진행한다.
            for (int i = 0;i<ja.length();i++){
                // JSONArray에서 추출한 값을 저장할 JSONObject를 선언하고 초기화한다.
                JSONObject jo = ja.getJSONObject(i);
                // movies 리스트에 JSONObject에서 값을 받아와 Movie객체로 추가한다.
                movies.add(new Movie(jo.getString("mid"), jo.getString("title"), jo.getString("openDay"),
                        jo.getString("director"), jo.getString("rating"), jo.getString("length")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
