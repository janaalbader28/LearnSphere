package com.example.learnsphere2.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnsphere2.Adapter.CourseUserAdapter;
import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.R;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {


    private FrameLayout frameBack;
    private TextView tvTypeCourses;
    private RecyclerView rvCourses;
    private CourseUserAdapter courseAdapter;
    private ArrayList<Course> courseList;
    private DataBase db;
    private String typeCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});
        findView();
        setCourseAdapter();}


    private void findView() {

        courseList = new ArrayList<>();
        db = new DataBase(CourseActivity.this);
        typeCourses = getIntent().getStringExtra("typeCourses");
        frameBack = findViewById(R.id.frameBack);
        tvTypeCourses = findViewById(R.id.tvTypeCourses);
        rvCourses = findViewById(R.id.rvCourses);
        tvTypeCourses.setText(typeCourses);
        frameBack.setOnClickListener(view -> {
            finish();
        });}

    @SuppressLint("NotifyDataSetChanged")
    private void setCourseAdapter() {
        db.open();
        courseList = db.getCourseByType(typeCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseUserAdapter(courseList);
        rvCourses.setAdapter(courseAdapter);
        db.close();}}
