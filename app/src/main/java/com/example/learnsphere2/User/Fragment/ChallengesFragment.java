package com.example.learnsphere2.User.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.learnsphere2.R;

public class ChallengesFragment extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenges, container, false);

        LinearLayout challenge1 = view.findViewById(R.id.Adevelopingbtn);
        LinearLayout challengeCodeCombat = view.findViewById(R.id.AUXUIbtn);
        LinearLayout challengeCoderHub = view.findViewById(R.id.AAIMLbtn);
        LinearLayout challengeScreeps = view.findViewById(R.id.Adatabtn);
        String codeCombatUrl = "https://codecombat.com";
        String coderHubUrl = "https://coderhub.com";
        String screepsUrl = "https://screeps.com";
        String CodewarsUrl = "https://www.codewars.com/";

        challenge1.setOnClickListener(v -> openWebsite(CodewarsUrl));

        challengeCodeCombat.setOnClickListener(v -> openWebsite(codeCombatUrl));

        challengeCoderHub.setOnClickListener(v -> openWebsite(coderHubUrl));

        challengeScreeps.setOnClickListener(v -> openWebsite(screepsUrl));
        return view;}

    private void openWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);}}