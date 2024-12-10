package com.example.learnsphere2.Admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnsphere2.LoginActivity;
import com.example.learnsphere2.Admin.PartnerContactsActivity;
import com.example.learnsphere2.R;

public class AdminSettingsFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_admin_settings, container, false);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        Button btnPartners=view.findViewById(R.id.btn_Updateprofile);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();});
        btnPartners.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PartnerContactsActivity.class);
            startActivity(intent);});
        return view;
    }}