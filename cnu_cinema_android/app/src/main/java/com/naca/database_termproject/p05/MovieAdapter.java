package com.naca.database_termproject.p05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.data.Movie;
import com.naca.database_termproject.databinding.MovieElementBinding;

import java.util.LinkedList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindingViewHolder> {

    // 출력할 영화 정보를 담아둔 Movie객체 LinkedList객체이다.
    private LinkedList<Movie> movies;

    // RecyclerView의 item에 대한 클릭 리스너를 interface로 초기화한다.
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 클릭 리스너를 선언하고 null로 초기화한다.
    public static OnItemClickListener mListener = null;

    // 생성자를 통해 LinkedList의 값을 받아온다.
    public MovieAdapter(LinkedList<Movie> movies) {
        this.movies = movies;
    }

    // 각 item에 대한 레이아웃을 연결해준다.
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieElementBinding binding = MovieElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    // itme들에 값을 입력해준다.
    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    // 전체 리스트의 크기를 반환한다.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // 클릭 리스너를 받아와 이전에 선언된 클릭 리스너를 연결해준다.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // item에 대한 설정을 진행하는 ViewHolder를 선언한다
    public class BindingViewHolder extends RecyclerView.ViewHolder {
        // item의 레이아웃 정보를 받아와 binding에 설정한다.
        MovieElementBinding binding;

        public BindingViewHolder(@NonNull MovieElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // itme이 클릭됐을 때 실행되는 클릭 리스너를 설정한다.
            // 리스너에 대한 정보는 Activity에서 처리된 것을 받아와 설정한다.
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(MovieAdapter.mListener != null) {
                            MovieAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        // Movie의 값을 받아와 item에 출력되도록 한다.
        public void bind(Movie movie) {
            binding.setVariable(BR.movie, movie);
        }
    }
}
