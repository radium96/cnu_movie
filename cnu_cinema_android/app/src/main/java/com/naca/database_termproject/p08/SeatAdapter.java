package com.naca.database_termproject.p08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.data.Schedule;
import com.naca.database_termproject.databinding.ScheduleElementBinding;
import com.naca.database_termproject.databinding.SeatElementBinding;

import java.util.LinkedList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.BindingViewHolder> {

    private LinkedList<Integer> allSeats;
    private LinkedList<Integer> disabled;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public SeatAdapter(LinkedList<Integer> allSeats, LinkedList<Integer> disabled) {
        this.allSeats = allSeats;
        this.disabled = disabled;
        Log.d("DISABLED", String.valueOf(disabled.size()));
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeatElementBinding binding = SeatElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(allSeats.get(position));
        for(int idx : disabled){
            if (position + 1 == idx){
                holder.itemView.setEnabled(false);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return allSeats.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        SeatElementBinding binding;

        public BindingViewHolder(@NonNull SeatElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(SeatAdapter.mListener != null) {
                            SeatAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(int seat) {
            binding.setVariable(BR.seat, seat);
        }
    }
}
