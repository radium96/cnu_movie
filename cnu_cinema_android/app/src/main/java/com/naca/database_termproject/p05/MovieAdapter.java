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

    private LinkedList<Movie> movies;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public MovieAdapter(LinkedList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieElementBinding binding = MovieElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        MovieElementBinding binding;

        public BindingViewHolder(@NonNull MovieElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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

        public void bind(Movie movie) {
            binding.setVariable(BR.movie, movie);
        }
    }
}
