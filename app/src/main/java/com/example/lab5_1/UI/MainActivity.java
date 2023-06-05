package com.example.lab5_1.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lab5_1.R;
import com.example.lab5_1.Data.User;
import com.example.lab5_1.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    private UserAdapter adapter = new UserAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserListViewModel viewModel = new ViewModelProvider(this).get(UserListViewModel.class);

        viewModel.addUser("Maxime Marchand", "m.marchand22@hotmail.com");
        viewModel.addUser("Francis Maynard", "f.maynard@hotmail.com");
        viewModel.addUser("Raphael Chenard Lamothe", "r.chenard.lamothe@hotmail.com");

        RecyclerView recyclerView = findViewById(R.id.RecylclerView_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        TextView txtEmptyList = findViewById(R.id.textEmptyList);

        adapter.callback = (user) -> {
            viewModel.removeUser(user);
        };
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.submitList(new ArrayList<>(users));
                if (users.size() > 0) {
                    txtEmptyList.setVisibility(View.GONE);
                } else {
                    txtEmptyList.setVisibility(View.VISIBLE);
                }
            }
        });

        ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            viewModel.addUser(data.getStringExtra("name"), data.getStringExtra("email"));
                        }
                    }
                });

        FloatingActionButton addbtn = findViewById(R.id.addbutton);
        addbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            mGetContent.launch(intent);
        });
    }
}