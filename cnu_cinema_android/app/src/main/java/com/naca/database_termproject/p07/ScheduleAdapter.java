package com.naca.database_termproject.p07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ScheduleElementBinding;

import java.util.LinkedList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.BindingViewHolder> {

    private LinkedList<Schedule> schedules;
    private String title;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public ScheduleAdapter(LinkedList<Schedule> schedules, String title) {
        this.schedules = schedules;
        this.title = title;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScheduleElementBinding binding = ScheduleElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        ScheduleElementBinding binding;

        public BindingViewHolder(@NonNull ScheduleElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(ScheduleAdapter.mListener != null) {
                            ScheduleAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Schedule schedule) {
            binding.setVariable(BR.schedule, schedule);
        }
    }
}
