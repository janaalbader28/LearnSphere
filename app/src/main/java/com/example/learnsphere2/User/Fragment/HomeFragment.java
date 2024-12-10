package com.example.learnsphere2.User.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.learnsphere2.User.CourseActivity;
import com.example.learnsphere2.User.StartUpActivity;
import com.example.learnsphere2.R;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout developingbtn = view.findViewById(R.id.developingbtn);
        developingbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CourseActivity.class);
            i.putExtra("typeCourses","Developing Courses");
            startActivity(i);});

        LinearLayout UXUIbtn = view.findViewById(R.id.UXUIbtn);
        UXUIbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CourseActivity.class);
            i.putExtra("typeCourses","UX/UI Courses");
            startActivity(i);});

        LinearLayout AIMLbtn = view.findViewById(R.id.AIMLbtn);
        AIMLbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CourseActivity.class);
            i.putExtra("typeCourses","AI & ML Courses");
            startActivity(i);});
        LinearLayout databtn = view.findViewById(R.id.databtn);
        databtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CourseActivity.class);
            i.putExtra("typeCourses","Data Courses");
            startActivity(i);});

        LinearLayout startnow = view.findViewById(R.id.startnow);
        startnow.setOnClickListener(v -> startActivity(new Intent(getActivity(), StartUpActivity.class)));

        return view;}}