package com.example.learnsphere2.User.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.AlertDialog;


import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.LoginActivity;
import com.example.learnsphere2.User.ContactUsActivity;
import com.example.learnsphere2.User.UpdateProfileActivity;
import com.example.learnsphere2.R;

public class SettingsFragment extends Fragment {
    private View view;
    private DataBase db;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        db = new DataBase(requireContext());

        Button btnLogout = view.findViewById(R.id.btn_logout);
        Button btnUpdateProfile = view.findViewById(R.id.btn_Updateprofile);
        Button btnContactUs = view.findViewById(R.id.btn_contact_us);
        Button btnDeleteAccount = view.findViewById(R.id.btnDeleteAccount);

        btnDeleteAccount.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        deleteUserAccount();
                    })
                    .setNegativeButton("No", null)
                    .show();});

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();});

        btnUpdateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
            startActivity(intent);});

        btnContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            startActivity(intent);});

        return view;}

    private void deleteUserAccount() {
        sharedPreferences = requireActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("id_user", -1);
        if (idUser != 0) {
            db.open();
            boolean isDeleted = db.deleteUser(idUser);
            db.close();
            if (isDeleted) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Account Deleted")
                        .setMessage("Your account has been successfully deleted.")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        })
                        .show();}
             else {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Failed to delete account. Please try again.")
                        .setPositiveButton("OK", null)
                        .show();}}
         else {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Failed to retrieve user information.")
                    .setPositiveButton("OK", null)
                    .show();}}}