package com.example.learnsphere2.Admin;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class AddCourseActivity extends AppCompatActivity {

    private FrameLayout frameBack;
    private ImageView imgCourse;
    private EditText edCourseTitle, edCourseInfo, edCourseDate, edCourseUrl;
    private Spinner spLocation;
    private Button btnAddCourse;

    private byte[] img_Course = null;
    private String location = "In Person", typeCourses;
    private static final int PICK_IMAGE = 1888;
    private DatePickerDialog datePickerDialog;
    private DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findView();
        showDatePickerDialog();
        setAdapterLocation();
    }

    private void findView() {

        db = new DataBase(this);
        typeCourses = getIntent().getStringExtra("typeCourses");

        frameBack = findViewById(R.id.frameBack);
        imgCourse = findViewById(R.id.imgCourse);
        edCourseTitle = findViewById(R.id.edCourseTitle);
        edCourseInfo = findViewById(R.id.edCourseInfo);
        edCourseDate = findViewById(R.id.edCourseDate);
        edCourseUrl = findViewById(R.id.edCourseUrl);
        spLocation = findViewById(R.id.spLocation);
        btnAddCourse = findViewById(R.id.btnAddCourse);

        frameBack.setOnClickListener(view -> {
            finish();});

        imgCourse.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE);});

        edCourseDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.show();
                edCourseDate.clearFocus();}});

        btnAddCourse.setOnClickListener(view -> {
            String courseTitle = edCourseTitle.getText().toString();
            String courseInfo = edCourseInfo.getText().toString();
            String courseDate = edCourseDate.getText().toString();
            String courseUrl = edCourseUrl.getText().toString();
            location = spLocation.getSelectedItem().toString();
            if (img_Course == null || courseTitle.isEmpty() || courseInfo.isEmpty() || courseDate.isEmpty() || courseUrl.isEmpty()) {
                Toast.makeText(AddCourseActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
            } else {
                db.open();
                Course data = new Course(img_Course, typeCourses, courseTitle, courseInfo, courseDate, courseUrl, location);
                boolean isAdded = db.addCourse(data);
                db.close();
                if (isAdded) {
                    Toast.makeText(this, "The Course Has Been Added Successfully", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddCourseActivity.this, "Error Adding Course!!", Toast.LENGTH_SHORT).show();}}});}

    private void setAdapterLocation() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.location,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter);}

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(AddCourseActivity.this, (datePicker, year1, month1, day1) -> {
            String selectedDate = (month1 + 1) + "/" + day1 + "/" + year1;
            edCourseDate.setText(selectedDate);
        }, year, month, day);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            Uri imageUri = data.getData();
            imgCourse.setImageURI(imageUri);

            InputStream iStream;
            try {
                iStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                img_Course = getBytes(iStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);}

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);}
        return byteBuffer.toByteArray();}}