package com.example.lab5_1.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5_1.R;


public class Fragment_Add_User extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__add__use_r, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserListViewModel viewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);

        Button btnAdd = view.findViewById(R.id.buttonAddUser);
        Button btnCancel = view.findViewById(R.id.buttonCancelUser);


        btnCancel.setOnClickListener(v -> {

            NavController navController = Navigation.findNavController(view);
            navController.navigateUp();
        });

        btnAdd.setOnClickListener(v -> {

            EditText nom = view.findViewById(R.id.nameinput);
            EditText email = view.findViewById(R.id.emailinput);
            if (nom.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Nom requis",
                        Toast.LENGTH_LONG).show();
            } else if (email.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Email requis",
                        Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                Toast.makeText(getContext(), "Email non valide",
                        Toast.LENGTH_LONG).show();
            } else {
                viewModel.addUser(nom.getText().toString(), email.getText().toString());
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
}