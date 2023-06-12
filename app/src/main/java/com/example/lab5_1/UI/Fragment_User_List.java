package com.example.lab5_1.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lab5_1.Data.User;
import com.example.lab5_1.R;
import com.example.lab5_1.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class Fragment_User_List extends Fragment {

    private UserAdapter adapter = new UserAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__user__list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserListViewModel viewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.RecylclerView_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        TextView txtEmptyList = view.findViewById(R.id.textEmptyList);

        adapter.callback = (user) -> {
            viewModel.removeUser(user);
        };
        viewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.submitList(users);
                if (users.size() > 0) {
                    txtEmptyList.setVisibility(View.INVISIBLE);
                } else {
                    txtEmptyList.setVisibility(View.VISIBLE);
                }
            }
        });
        FloatingActionButton addbtn = view.findViewById(R.id.addbutton);
        addbtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragment_User_List_to_fragment_Add_User);
        });
    }
}