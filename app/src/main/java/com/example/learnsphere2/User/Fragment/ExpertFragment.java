package com.example.learnsphere2.User.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learnsphere2.R;


public class ExpertFragment extends Fragment {
    private EditText phoneEditText, emailEditText, questionsEditText;
    private ImageView uploadedImageView;
    private Uri imageUri;
    private View view;

    private ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    uploadedImageView.setImageURI(imageUri);
                    uploadedImageView.setVisibility(View.VISIBLE);}});

    private void checkAndRequestPermissions() {
        if (getActivity() != null &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);}}

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expert, container, false);

        checkAndRequestPermissions();

        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        questionsEditText = view.findViewById(R.id.questionsEditText);
        uploadedImageView = view.findViewById(R.id.uploadedImageView);

        Button uploadImageButton = view.findViewById(R.id.uploadImageButton);
        Button submitButton = view.findViewById(R.id.submitButton);

        uploadImageButton.setOnClickListener(v -> openImageChooser());
        submitButton.setOnClickListener(v -> submitForm());

        return view;}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();}}}

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);}

    private void submitForm() {
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String questionsAnswer = questionsEditText.getText().toString();

        if (phone.isEmpty() || phone.length() < 10) {
            phoneEditText.setError("Please enter a valid phone number");
            return;}

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            return;}

        if (questionsAnswer.isEmpty()) {
            questionsEditText.setError("Please write your answer");
            return;}

        Toast.makeText(getActivity(), "Form Submitted!" , Toast.LENGTH_LONG).show();

        if (imageUri != null) {
            Toast.makeText(getActivity(), "Image selected: " + imageUri.toString(), Toast.LENGTH_SHORT).show();}

        phoneEditText.setText("");
        emailEditText.setText("");
        questionsEditText.setText("");
        uploadedImageView.setImageURI(null);
        uploadedImageView.setVisibility(View.GONE);
        imageUri = null;}}

