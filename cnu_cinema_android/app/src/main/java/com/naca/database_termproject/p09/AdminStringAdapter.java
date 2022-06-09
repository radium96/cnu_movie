package com.naca.database_termproject.p09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naca.database_termproject.BR;
import com.naca.database_termproject.databinding.AdminStringElementBinding;

import java.util.LinkedList;

public class AdminStringAdapter extends RecyclerView.Adapter<AdminStringAdapter.BindingViewHolder> {

    // 출력할 질의 결과문을 담아둔 String객체 LinkedList객체이다.
    private LinkedList<String> strs;

    // 생성자를 통해 LinkedList의 값을 받아온다.
    public AdminStringAdapter(LinkedList<String> strs) {
        this.strs = strs;
    }

    // 각 item에 대한 레이아웃을 연결해준다.
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminStringElementBinding binding = AdminStringElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder(binding);
    }

    // itme들에 값을 입력해준다.
    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(strs.get(position));
    }

    // 전체 리스트의 크기를 반환한다.
    @Override
    public int getItemCount() {
        return strs.size();
    }

    // item에 대한 설정을 진행하는 ViewHolder를 선언한다.
    public class BindingViewHolder extends RecyclerView.ViewHolder {
        // item의 레이아웃 정보를 받아와 binding에 설정한다.
        AdminStringElementBinding binding;

        public BindingViewHolder(@NonNull AdminStringElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // 질의문에 대한 값을 받아와 item에 출력되도록 한다.
        public void bind(String str) {
            binding.setVariable(BR.strs, str);
        }
    }
}
