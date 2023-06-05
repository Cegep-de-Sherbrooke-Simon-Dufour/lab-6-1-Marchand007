package com.example.lab5_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.intellij.lang.annotations.Pattern;

import java.util.ArrayList;

public class AddUserActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_layout);

        Button btnAdd = findViewById(R.id.buttonAddUser);
        Button btnCancel = findViewById(R.id.buttonCancelUser);

        btnAdd.setOnClickListener(addUser);
        btnCancel.setOnClickListener(cancelUser);
    }

    View.OnClickListener cancelUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setResult(RESULT_CANCELED);
            finish();
        }
    };

    View.OnClickListener addUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText nom = findViewById(R.id.nameinput);
            EditText email = findViewById(R.id.emailinput);
            if (nom.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Nom requis",
                        Toast.LENGTH_LONG).show();
            }
            else if (email.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Email requis",
                        Toast.LENGTH_LONG).show();
            }
             else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

                Toast.makeText(getApplicationContext(), "Email non valide",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", nom.getText().toString());
                resultIntent.putExtra("email", email.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    };


}
