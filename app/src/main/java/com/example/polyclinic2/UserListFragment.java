package com.example.polyclinic2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment implements UserAdapter.OnItemClickUserListener {
    private RecyclerView recyclerViewUser;
    private UserAdapter userAdapter;
    private List<User> userList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference registeredUsersRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        recyclerViewUser = view.findViewById(R.id.recyclerViewDoc);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, getActivity(), this);
        recyclerViewUser.setAdapter(userAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        registeredUsersRef = mDatabase.getReference("users");
        registeredUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        userList.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });

        SearchView searchView = view.findViewById(R.id.searchUserView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        Button searchButton = view.findViewById(R.id.searchUsers);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст из SearchView
                String query = searchView.getQuery().toString();
                // Обновляем список на основе введенного пользователем текста
                filter(query);
            }
        });

        return view;
    }

    @Override
    public void onItemUserClick(User user) {
        String userId = user.getId();

        UserProfileFragment userProfileFragment = new UserProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_ID, userId);
        userProfileFragment.setArguments(bundle);
    }

    public void filter(String text) {
        ArrayList<User> filteredList = new ArrayList<>();

        for (User user : userList) {
            if (user.getFio().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        userAdapter.setUserList(filteredList);
    }
}