package com.example.learnsphere2.Admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.learnsphere2.Admin.AdminCourseActivity;
import com.example.learnsphere2.R;

public class AdminHomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        // Developing Courses Button
        LinearLayout developingbtn = view.findViewById(R.id.Adevelopingbtn);
        developingbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AdminCourseActivity.class);
            i.putExtra("typeCourses","Developing Courses");
            startActivity(i);});

        // UX/UI Courses Button
        LinearLayout UXUIbtn = view.findViewById(R.id.AUXUIbtn);
        UXUIbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AdminCourseActivity.class);
            i.putExtra("typeCourses","UX/UI Courses");
            startActivity(i);});

        // AI & ML Courses Button
        LinearLayout AIMLbtn = view.findViewById(R.id.AAIMLbtn);
        AIMLbtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AdminCourseActivity.class);
            i.putExtra("typeCourses","AI & ML Courses");
            startActivity(i);});

        // Data Courses Button
        LinearLayout databtn = view.findViewById(R.id.Adatabtn);
        databtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AdminCourseActivity.class);
            i.putExtra("typeCourses","Data Courses");
            startActivity(i);});

        return view;}}