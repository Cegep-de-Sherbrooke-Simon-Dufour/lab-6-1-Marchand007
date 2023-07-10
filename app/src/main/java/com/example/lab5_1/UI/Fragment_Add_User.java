package com.example.lab5_1.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab5_1.R;

import java.io.File;
import java.util.UUID;


public class Fragment_Add_User extends Fragment {

    private Uri uri;

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
        Button btnPrendrePhoto = view.findViewById(R.id.btnPrendrePhoto);
        Button btnChoisirPhoto = view.findViewById(R.id.btnChoisirPhoto);
        ImageView userImagePreview = view.findViewById(R.id.user_image_preview);

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
                if (uri == null) {
                    viewModel.addUser(nom.getText().toString(), email.getText().toString());
                } else {
                    viewModel.addUser(nom.getText().toString(), email.getText().toString(), uri.toString());
                }
                Navigation.findNavController(view).navigateUp();
            }
        });
        ActivityResultLauncher<Uri> cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                pictureTaken -> {
                    if (pictureTaken) {
                        userImagePreview.setImageURI(uri);
                    }
                }
        );
        btnPrendrePhoto.setOnClickListener(v -> {
            File folder = new File(requireContext().getFilesDir(), "user_images");
            if (!folder.exists()) folder.mkdirs();
            File file = new File(folder, UUID.randomUUID().toString() + ".png");
            uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".provider",
                    file);
            cameraResultLauncher.launch(uri);
        });

        ActivityResultLauncher<PickVisualMediaRequest> galleryResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.PickVisualMedia(),
                        uri -> {
                            userImagePreview.setImageURI(uri);
                        });
        btnChoisirPhoto.setOnClickListener(v -> {
            galleryResultLauncher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());

        });
    }
}