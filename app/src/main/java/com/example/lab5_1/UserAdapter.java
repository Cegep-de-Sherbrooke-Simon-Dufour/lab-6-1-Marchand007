package com.example.lab5_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_1.Data.User;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> {


    public RecyclerCallback<User> callback = (U) -> {
    };

    public UserAdapter() {
        super(new DiffUtil.ItemCallback<>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getNom().equals(newItem.getNom()) && oldItem.getEmail().equals(newItem.getNom());
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _nom;
        private TextView _email;
        private ImageView userImage;
        private User user;


        public ViewHolder(@NonNull View actualView) {
            super(actualView);
            _nom = actualView.findViewById(R.id.user_name);
            _email = actualView.findViewById(R.id.user_email);
            userImage = actualView.findViewById(R.id.user_image_list);

            actualView.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(actualView.getContext(), actualView);
//
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_users, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    if (menuItem.getTitle().equals("Supprimer")) {
                        new AlertDialog.Builder(actualView.getContext()).setTitle("Confirmation de suppresion").setMessage("Êtes-vous vraiment sur de vouloir supprimer " + _nom.getText())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(R.string.confirmdelete, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        callback.returnValue(user);
                                    }
                                }).setNegativeButton(R.string.confirmnodelete, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).show();
                    } else if ((menuItem.getTitle().equals("Locations"))) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", user.getEmail());
                        bundle.putString("userNom", user.getNom());
                        bundle.putString("userImage", user.getUri());
                        Navigation.findNavController(v).navigate(R.id.action_fragment_User_List_to_fragment_Location_List_For_A_User, bundle);
                    } else if ((menuItem.getTitle().equals("Modifier"))) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", user.getEmail());
                        bundle.putString("userNom", user.getNom());
                        bundle.putString("userImage", user.getUri());
                        Navigation.findNavController(v).navigate(R.id.action_fragment_User_List_to_fragment_User_Info, bundle);
                    }
                    return true;
                });
                popupMenu.show();
            });

            ImageButton deletebtn = actualView.findViewById(R.id.deletebutton);
            deletebtn.setOnClickListener(view ->

            {
                new AlertDialog.Builder(actualView.getContext()).setTitle("Confirmation de suppresion").setMessage("Êtes-vous vraiment sur de vouloir supprimer " + _nom.getText()).setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(R.string.confirmdelete, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.returnValue(user);
                    }
                }).setNegativeButton(R.string.confirmnodelete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
            });

        }

        public void bind(User user) {
            _nom.setText(user.getNom());
            _email.setText(user.getEmail());
            if (user.getUri() != null) {
                userImage.setImageURI(Uri.parse(user.getUri()));
            } else {
                userImage.setImageResource(R.drawable.userimage);
            }
            this.user = user;
        }

    }

}
