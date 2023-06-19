package com.example.lab5_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_1.Data.Location;
import com.example.lab5_1.Data.User;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.ViewHolder> {


    public RecyclerCallback<Location> callbackDeleteLocation = (U) -> {
    };
    public RecyclerCallback<String> callbackDeleteUserWithEmail = (U) -> {
    };
    public RecyclerCallback<Location> callbackAdd = (U) -> {
    };

    public LocationAdapter() {
        super(new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
                return oldItem.getNom().equals(newItem.getNom()) && oldItem.getUserEmail().equals(newItem.getUserEmail());
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _nomLocation;
        private Location location;


        public ViewHolder(@NonNull View actualView) {
            super(actualView);
            _nomLocation = actualView.findViewById(R.id.location_name);

            actualView.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(actualView.getContext(), actualView);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_locations, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    if (menuItem.getTitle().equals("Supprimer")) {
                        new AlertDialog.Builder(actualView.getContext()).setTitle("Confirmation de suppresion").setMessage("Êtes-vous vraiment sur de vouloir supprimer " + _nomLocation.getText())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(R.string.confirmdelete, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        callbackDeleteLocation.returnValue(location);
                                    }
                                }).setNegativeButton(R.string.confirmnodelete, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                    // Toast message on menu item clicked
                    //Toast.makeText(actualView.getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                });
                popupMenu.show();
            });

            ImageButton deletebtn = actualView.findViewById(R.id.deletebutton2);
            deletebtn.setOnClickListener(view ->
            {
                new AlertDialog.Builder(actualView.getContext()).setTitle("Confirmation de suppresion").setMessage("Êtes-vous vraiment sur de vouloir supprimer " + _nomLocation.getText()).setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(R.string.confirmdelete, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        callbackDeleteLocation.returnValue(location);
                    }
                }).setNegativeButton(R.string.confirmnodelete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
            });
        }

        public void bind(Location location) {
            _nomLocation.setText(location.getNom());
            this.location = location;
        }

    }

}
