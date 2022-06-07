package com.naca.database_termproject.p09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.data.Ticket;
import com.naca.database_termproject.databinding.AdminElementBinding;
import com.naca.database_termproject.databinding.TicketingElementBinding;

import java.util.LinkedList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.BindingViewHolder> {

    private LinkedList<Ticket> tickets;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public static OnItemClickListener mListener = null;

    public AdminAdapter(LinkedList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminElementBinding binding = AdminElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(tickets.get(position));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        AdminElementBinding binding;

        public BindingViewHolder(@NonNull AdminElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(AdminAdapter.mListener != null) {
                            AdminAdapter.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Ticket ticket) {
            binding.setVariable(BR.admin, ticket);
        }
    }
}
