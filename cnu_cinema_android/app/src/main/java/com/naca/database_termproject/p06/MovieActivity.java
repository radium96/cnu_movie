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
import com.naca.database_termproject.task.QueryTask;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.databinding.MovieActivityBinding;
import com.naca.database_termproject.p07.ReserveActivity;

public class MovieActivity extends AppCompatActivity {

    // 해당 Activity의 view를 받아오는 Binding객체이다.
    private MovieActivityBinding binding;
    // 이전 Activity에서 선택된 영화의 정보를 저장하는 Movie객체이다.
    private Movie movie;
    // 해당 영화에 대한 예약 관객수와 누적 관객수를 저장할 String배열 객체이다.
    private String[] cnt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding과 view를 연결해준다.
        binding = DataBindingUtil.setContentView(this, R.layout.p06_activity_movie);
        binding.setLifecycleOwner(this);

        // 이전 Activity에서 영화 정보를 받아와 movie에 저장한다.
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("select");

        // 데이터베이스에서 해당 영화에 대한 예약 관객수와 누적 관객수를 불러온다.
        loadCount();

        // 영화의 정보와 관객수를 view에 출력한다.
        bind(movie);

        // 예매하기 버튼 객체를 view에서 받아온다.
        TextView reservation = binding.toolbar06.p6Content.reservation;

        // 예매하기 버튼을 클릭했을 때 기재된 onClickListener를 실행한다.
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, ReserveActivity.class);
                // 영화의 정보를 예약 Activity로 넘겨준다.
                intent.putExtra("select", movie);
                // 예약 Activity를 실행한다.
                startActivity(intent);
            }
        });
    }

    // 데이터베이스에서 영화의 예약 관객수와 누적 관객수를 받아온다.
    void loadCount(){
        try{
            // 영화의 예약 관객수와 누적 관객수를 받아오는 쿼리를 작성한다.
            String query = "SELECT COUNT(CASE WHEN T.STATUS = 'W' THEN 1 END) cnt1, COUNT(CASE WHEN T.STATUS = 'R' THEN 1 END) cnt2 " +
                    "FROM TICKETING T, MOVIE_SCHEDULE S WHERE T.SID = S.SID AND S.MID = '" + movie.getMid() + "'";
            // 쿼리를 전달할 QueryTask를 생성하고 moviedetail로 접속한다.
            QueryTask task = new QueryTask("moviedetail");
            // task에 query를 값으로 넣고 결과값을 result에 저장한다.
            // result는 예약 관객수와 누적 관객수를 전달받는다.
            String result = task.execute(query).get();
            Log.d("RESULT: ", result);
            // 두 값을 공백을 기준으로 나눠 cnt에 저장한다.
            cnt = result.split(" ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 영화의 정보를 view에 갱신한다.
    public void bind(Movie movie){
        binding.toolbar06.p6Content.setVariable(BR.selected, movie);
        binding.toolbar06.p6Content.setVariable(BR.cnt1, cnt[0]);
        binding.toolbar06.p6Content.setVariable(BR.cnt2, cnt[1]);
    }
}
