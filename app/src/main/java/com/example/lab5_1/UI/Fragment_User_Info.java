package com.example.lab5_1.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import com.example.lab5_1.R;

import java.io.File;
import java.util.UUID;


public class Fragment_User_Info extends Fragment {

    private Uri uri;

    EditText inputNom;
    EditText inputEmail;
    ImageView userImagePreview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserListViewModel viewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);

        Button btnUpdate = view.findViewById(R.id.buttonUpdateUser_info);
        Button btnCancel = view.findViewById(R.id.buttonCancelUser_info);
        Button btnPrendrePhoto = view.findViewById(R.id.btnPrendrePhoto_info);
        Button btnChoisirPhoto = view.findViewById(R.id.btnChoisirPhoto_info);
        inputNom = view.findViewById(R.id.nameinput_info);
        inputEmail = view.findViewById(R.id.emailinput_info);
        userImagePreview = view.findViewById(R.id.user_image_preview_info);

        inputNom.setText(getArguments().getString("userNom"));
        inputEmail.setText(getArguments().getString("userEmail"));
        if (getArguments().getString("userImage") != null) {
            uri = Uri.parse(getArguments().getString("userImage"));
            userImagePreview.setImageURI(uri);
        }
        inputEmail.setEnabled(false);

        btnCancel.setOnClickListener(v -> {

            NavController navController = Navigation.findNavController(view);
            navController.navigateUp();
        });

        btnUpdate.setOnClickListener(v -> {

            if (inputNom.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Nom requis",
                        Toast.LENGTH_LONG).show();
            } else if (inputEmail.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Email requis",
                        Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                Toast.makeText(getContext(), "Email non valide",
                        Toast.LENGTH_LONG).show();
            } else {
                viewModel.updateUser(inputNom.getText().toString(), inputEmail.getText().toString(), uri.toString());
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
                            int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            requireContext().getContentResolver().takePersistableUriPermission(uri,
                                    flags);
                        });
        btnChoisirPhoto.setOnClickListener(v -> {
            galleryResultLauncher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
    }
}