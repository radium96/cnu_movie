package com.naca.database_termproject.p09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.databinding.AdminElementBinding;
import com.naca.database_termproject.databinding.AdminStringElementBinding;

import java.util.LinkedList;

public class AdminStringAdapter extends RecyclerView.Adapter<AdminStringAdapter.BindingViewHolder> {

    private LinkedList<String> strs;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public AdminStringAdapter(LinkedList<String> strs) {
        this.strs = strs;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminStringElementBinding binding = AdminStringElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(strs.get(position));
    }

    @Override
    public int getItemCount() {
        return strs.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        AdminStringElementBinding binding;

        public BindingViewHolder(@NonNull AdminStringElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(AdminStringAdapter.mListener != null) {
                            AdminStringAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(String str) {
            binding.setVariable(BR.strs, str);
        }
    }
}
