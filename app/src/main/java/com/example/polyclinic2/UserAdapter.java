package com.example.polyclinic2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnItemClickUserListener listenerUser;

    public UserAdapter(List<User> userList, OnItemClickUserListener listenerUser) {

        this.userList = userList;
        this.listenerUser = listenerUser;
    }

    public interface OnItemClickUserListener{
        void onItemUserClick(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.fio.setText(user.getFio());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerUser.onItemUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount(){
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView fio;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fio = itemView.findViewById(R.id.fio_list);
        }
    }
}
