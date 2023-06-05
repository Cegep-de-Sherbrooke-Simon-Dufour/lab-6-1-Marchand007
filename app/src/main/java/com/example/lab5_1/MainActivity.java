package com.example.lab5_1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    private UserAdapter adapter = new UserAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users.add(new User("Maxime Marchand", "m.marchand22@hotmail.com"));
        users.add(new User("Francis Maynard", "f.maynard@hotmail.com"));
        users.add(new User("Raphael Chenard Lamothe", "r.chenard.lamothe@hotmail.com"));

        RecyclerView recyclerView = findViewById(R.id.RecylclerView_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.submitList(new ArrayList<>(users));

        adapter.callback = (user) -> {
            users.remove(user);
            adapter.submitList(new ArrayList<>(users));

            TextView txtEmptyList = findViewById(R.id.textEmptyList);
            if (users.size() == 0) {
                txtEmptyList.setVisibility(View.VISIBLE);
            }
        };

        FloatingActionButton addbtn = findViewById(R.id.addbutton);
        addbtn.setOnClickListener(adduser);

    }


    View.OnClickListener adduser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
           mGetContent.launch(intent);
        }
    };

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        users.add(new User(data.getStringExtra("name"), data.getStringExtra("email")));
                        adapter.submitList(new ArrayList<>(users));
                        TextView txtEmptyList = findViewById(R.id.textEmptyList);
                        if (users.size() > 0) {
                            txtEmptyList.setVisibility(View.GONE);
                        }
                    }
                }
            });
}