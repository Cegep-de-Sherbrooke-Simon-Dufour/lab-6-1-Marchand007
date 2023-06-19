package com.example.lab5_1.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_1.Data.Location;
import com.example.lab5_1.Data.User;
import com.example.lab5_1.LocationAdapter;
import com.example.lab5_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class Fragment_Location_List_For_A_User extends Fragment {

    private LocationAdapter adapter = new LocationAdapter();
    private TextView _nomUser;
    private TextView _emailUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__location__list_for_a_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _emailUser = view.findViewById(R.id.user_email_list);
        _nomUser = view.findViewById(R.id.user_name_list);
        _emailUser.setText(getArguments().getString("userEmail"));
        _nomUser.setText(getArguments().getString("userNom"));
        UserListViewModel viewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.RecylclerView_Location);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.callbackDeleteUserWithEmail = (userEmail) -> {

            viewModel.removeUserWithEmail(userEmail);
        };
        adapter.callbackDeleteLocation = (location) -> {
            viewModel.deleteLocation(location);
        };
        adapter.callbackAdd = (location) -> {
            viewModel.addLocation(location);
        };
        viewModel.getLocationsFromAUser(getArguments().getString("userEmail")).observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                adapter.submitList(locations);

            }
        });
        Button addbtn = view.findViewById(R.id.button_add_location);
        addbtn.setOnClickListener(v -> {
            EditText locationName = view.findViewById(R.id.input_nom_location_to_add);
            Location locationToAdd = new Location(locationName.getText().toString(), getArguments().getString("userEmail"));
            adapter.callbackAdd.returnValue(locationToAdd);
            locationName.setText("");
        });

        Button deletebtn = view.findViewById(R.id.button_supprimer);
        deletebtn.setOnClickListener(v -> {
            new AlertDialog.Builder(view.getContext()).setTitle("Confirmation de suppresion").setMessage("Êtes-vous vraiment sur de vouloir supprimer " + _nomUser.getText().toString())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.confirmdelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            adapter.callbackDeleteUserWithEmail.returnValue(_emailUser.getText().toString());
                            NavController navController = Navigation.findNavController(view);
                            navController.navigateUp();
                        }
                    }).setNegativeButton(R.string.confirmnodelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
        });
    }
}