package com.example.learnsphere2.Admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnsphere2.Adapter.CourseAdminAdapter;
import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminCourseActivity extends AppCompatActivity {


    private FrameLayout frameBack;
    private TextView tvTypeCourses;
    private RecyclerView rvCourses;
    private FloatingActionButton fabAddCourse;
    private CourseAdminAdapter courseAdapter;
    private ArrayList<Course> courseList;
    private DataBase db;
    private String typeCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findView();
        setCourseAdapter();
    }


    private void findView() {
        courseList = new ArrayList<>();
        db = new DataBase(AdminCourseActivity.this);
        typeCourses = getIntent().getStringExtra("typeCourses");

        frameBack = findViewById(R.id.frameBack);
        tvTypeCourses = findViewById(R.id.tvTypeCourses);
        rvCourses = findViewById(R.id.rvCourses);
        fabAddCourse = findViewById(R.id.fab_add_course);

        tvTypeCourses.setText(typeCourses);

        frameBack.setOnClickListener(view -> {
            finish();
        });

        fabAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCourseActivity.this, AddCourseActivity.class);
            intent.putExtra("typeCourses", typeCourses);
            startActivityForResult(intent, 200);
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setCourseAdapter() {
        db.open();
        courseList.clear();
        courseList = db.getCourseByType(typeCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdminAdapter(courseList);
        rvCourses.setAdapter(courseAdapter);
        db.close();
        courseAdapter.setOnItemDeleteListener((parent, view, position) -> {
            Course data = (Course) parent;
            db.open();
            boolean isDeleted = db.deleteCourse(data.getId());
            db.close();
            if (isDeleted) {
                courseList.remove(position);
                courseAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AdminCourseActivity.this, "Error Deleting Course!!", Toast.LENGTH_SHORT).show();
            }});

        courseAdapter.setOnItemEditListener((parent, view, position) -> {
            Course data = (Course) parent;
            Intent intent = new Intent(AdminCourseActivity.this, EditCourseActivity.class);
            intent.putExtra("dataCourse", data);
            startActivityForResult(intent , 200);
        });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            setCourseAdapter();
        }
        super.onActivityResult(requestCode, resultCode, data);}}
